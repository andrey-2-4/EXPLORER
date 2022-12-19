package org.example;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

public class Explorer {
    // private String rootPath;
    private ArrayList<FileInfo> filesInfo = new ArrayList<>();
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
        Collections.sort(filesInfo);
        concatenateAllFiles();
    }

    /**
     * Метод для вывода результата конкатенации
     */
    public void printConcatenationResult() {
        // TODO
    }

    /**
     * Если путь корректен, то this.rootPath = rootPath, иначе выбросит исключение
     * @param rootPath корневой путь
     * @throws RuntimeException Если некорректный корневой путь
     */
    private void checkAndSaveRootPath (String rootPath) throws RuntimeException {
        //
        try {
            File rootFile = new File(rootPath);
            if (!rootFile.isDirectory()) {
                throw new RuntimeException();
            }
            // this.rootPath = rootPath;
            requireString = requireString + rootPath;
        } catch (Exception exception) {
            throw new RuntimeException("НЕПРАВИЛЬНЫЙ КОРНЕВОЙ ПУТЬ");
        }
    }

    /**
     * Находит все файлы и зависимости, записывает их в filesAndRequiredFiles и в files
     * Если будет обнаружен не .txt файл, то он будет проигнорирован
     * @throws RuntimeException
     */
    private void findAllFilesAndRequirements(File rootFile) throws RuntimeException {
        //
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
     * Проверка на наличие циклов в зависимостях
     * @throws RuntimeException Если есть циклы в зависимостях
     */
    private void checkForNoCycles() throws RuntimeException {
        // TODO мой дебильный алгоритм (берем файл, идем по его зависимостям, отмечаемся, что вот мы
        //  типа пришли, если пришли второй раз, то выбрасываемся, а так идем дальше по зависимостям наших зависимостей;
        //  потом переходим к следующему файлику и сбрасываем "прихождения (надо будет boolean что-то сделать)", да)
        //  А ещё надо написать, кто во всём виноват (выводим все зависимости файла (а может просто сам файл),
        //  с которым мы работали).
        throw new RuntimeException("ЦИКЛИЧЕСКИЕ ЗАВИСИМОСТИ (ну или одна по крайней мере)");
    }

    /**
     * Получаем результат конкатенации списка файлов (меняем resultOfConcatenation)
     */
    private void concatenateAllFiles() throws RuntimeException {
        // TODO
        // Наверное и не рантайм экспшн
        // Но что-то наверняка выбрасывается
        // Подправить, когда станет ясно, что (в комментариях тоже)
    }

    /**
     * Вспомогательный класс для того, чтобы отсортировать файлы
     */
    private class FileInfo implements Comparable<FileInfo> {
        String fileName;
        String textOfFile;
        List<String> requiredFiles;
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
                requiredFiles.add(string);
            }
        }

        @Override
        public int compareTo(FileInfo otherFileInfo) {
            // TODO сортировка в зависимости от зависимостей :D (типа если зависит, то больше или меньше)
            return 0;
        }
    }
}
