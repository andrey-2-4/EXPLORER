package org.example;

import java.io.File;
import java.util.*;

public class Explorer {
    private String rootPath;
    private final ArrayList<FileInfo> filesInfo = new ArrayList<>();
    private ArrayList<Boolean> maskForCycleChecking;
    private String resultOfConcatenation;
    private String requireString = "require '";

    /**
     * Инициализирует класс
     * @param rootPath Корневой путь
     */
    public void initialize(String rootPath) {
        checkAndSaveRootPath(rootPath);
        findAllFilesAndRequirements(new File(rootPath));
        checkForNoCycles();
        filesInfo.sort(FileInfo::compareTo);
        concatenateAllFiles();
    }

    /**
     * Метод для вывода результата конкатенации
     */
    public void printConcatenationResult() {
        System.out.println(resultOfConcatenation);
    }

    /**
     * Обнуляет маску
     */
    private void setMaskForCycleChecking() {
        maskForCycleChecking = new ArrayList<>();
        for (int i = 0; i < filesInfo.size(); ++i) {
            maskForCycleChecking.add(false);
        }
    }

    /**
     * Получаем результат конкатенации списка файлов (меняем resultOfConcatenation)
     */
    private void concatenateAllFiles() throws RuntimeException {
        resultOfConcatenation = "";
        for (FileInfo fileInfo : filesInfo) {
            resultOfConcatenation = resultOfConcatenation.concat(fileInfo.textOfFile);
        }
    }

    /**
     * Если путь корректен, то this.rootPath = rootPath, иначе выбросит исключение
     * @param rootPath корневой путь
     * @throws RuntimeException Если некорректный корневой путь
     */
    private void checkAndSaveRootPath (String rootPath) throws RuntimeException {
        try {
            File rootFile = new File(rootPath);
            if (!rootFile.isDirectory()) {
                throw new RuntimeException();
            }
            this.rootPath = rootPath;
            rootPath = rootPath.substring(rootPath.lastIndexOf(File.separator) + 1);
            requireString = requireString + rootPath;
        } catch (Exception exception) {
            throw new RuntimeException("НЕПРАВИЛЬНЫЙ КОРНЕВОЙ ПУТЬ");
        }
    }

    /**
     * Находит все файлы и зависимости, записывает их в filesAndRequiredFiles и в files
     * Если будет обнаружен не .txt файл, то он будет проигнорирован
     * @throws RuntimeException ОШИБКА ВО ВРЕМЯ ПОИСКА ФАЙЛОВ И ЗАВИСИМОСТЕЙ
     */
    private void findAllFilesAndRequirements(File rootFile) throws RuntimeException {
        try {
            File[] directoryFiles = rootFile.listFiles();
            if (directoryFiles != null) {
                for (File file : directoryFiles) {
                    if (file.isDirectory()) {
                        findAllFilesAndRequirements(file);
                    } else {
                        if (file.getName().toLowerCase().endsWith(".txt")) {
                            filesInfo.add(new FileInfo(file.getAbsolutePath()));
                        }
                    }
                }
            }
        } catch (Exception exception) {
            throw new RuntimeException("ОШИБКА ВО ВРЕМЯ ПОИСКА ФАЙЛОВ И ЗАВИСИМОСТЕЙ");
        }
    }

    /**
     * Проверка на наличие циклов в зависимостях в списке всех файлов
     * @throws RuntimeException Если есть циклы в зависимостях
     */
    private void checkForNoCycles() throws RuntimeException {
        for (FileInfo fileInfo : filesInfo) {
            setMaskForCycleChecking();
            checkForNoCycles(fileInfo);
        }
    }

    /**
     * Проверка на наличие циклов в зависимостях ОДНОГО файла
     * @throws RuntimeException Если есть циклы в зависимостях
     */
    private void checkForNoCycles(FileInfo fileInfo) throws RuntimeException {
        if (fileInfo.requiredFiles != null) {
            for (String requiredFile : fileInfo.requiredFiles) {
                for (int i = 0; i < filesInfo.size(); ++i) {
                    if (filesInfo.get(i).fileName.equals(requiredFile)) {
                        if (maskForCycleChecking.get(i)) {
                            throw new RuntimeException("ЦИКЛИЧЕСКИЕ ЗАВИСИМОСТИ (ну или одна по крайней мере)");
                        }
                        maskForCycleChecking.set(i, true);
                        checkForNoCycles(filesInfo.get(i));
                    }
                }
            }
        }
    }

    /**
     * Вспомогательный класс для того, чтобы отсортировать файлы
     */
    private class FileInfo implements Comparable<FileInfo> {
        String fileName;
        String textOfFile;
        ArrayList<String> requiredFiles = new ArrayList<>();
        private FileInfo(String fileName) {
            this.fileName = fileName;
            saveTextAndRequiredFiles();
        }

        private void saveTextAndRequiredFiles() {
            try {
                File file = new File(fileName);
                Scanner in = new Scanner(file);
                textOfFile = "";
                String line;
                while(in.hasNextLine()) {
                    line = in.nextLine();
                    addRequirements(line);
                    textOfFile = textOfFile.concat(line + "\n");
                }
            } catch (Exception exception) {
                throw new RuntimeException("ОШИБКА ПРИ РАБОТЕ С ФАЙЛОМ " + fileName);
            }
        }

        private void addRequirements(String string) {
            if (string.startsWith(requireString)) {
                string = string.replaceAll("'", "");
                string = string.replaceFirst("require ", "");
                string = string.substring(string.indexOf(File.separator) + 1);
                string = rootPath + File.separator + string;
                requiredFiles.add(string);
            }
        }

        @Override
        public int compareTo(FileInfo otherFileInfo) {
            if (this.requiredFiles != null && this.requiredFiles.contains(otherFileInfo.fileName)) {
                return 1;
            } else if (otherFileInfo.requiredFiles != null && otherFileInfo.requiredFiles.contains(this.fileName)) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
