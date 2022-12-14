package org.example;

import java.util.*;

public class Explorer {
    private String rootPath;
    private Map<String, List<String>> filesAndRequiredFiles;
    private ArrayList<FileInfo> files;
    private String resultOfConcatenation;

    /**
     * Инициализирует класс
     * @param rootPath Корневой путь
     */
    public void initialize(String rootPath) {
        checkAndSaveRootPath(rootPath);
        findAllFilesAndRequirements();
        checkForNoCycles();
        Collections.sort(files);
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
     * @param rootPath
     * @throws RuntimeException Если некорректный корневой путь
     */
    private void checkAndSaveRootPath (String rootPath) throws RuntimeException {
        // TODO
        // Наверное и не рантайм экспшн
        // Но что-то наверняка выбрасывается
        // Подправить, когда станет ясно, что (в комментариях тоже)
    }

    /**
     * Находит все файлы и зависимости, записывает их в filesAndRequiredFiles и в files
     * Если будет обнаружен не .txt файл, то он будет проигнорирован
     * @throws RuntimeException
     */
    private void findAllFilesAndRequirements() throws RuntimeException {
        // TODO
        // Наверное и не рантайм экспшн
        // Но что-то наверняка выбрасывается
        // Подправить, когда станет ясно, что (в комментариях тоже)
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
        List<String> requiredFiles;
        private FileInfo(String fileName, List<String> requiredFiles) {
            this.fileName = fileName;
            this.requiredFiles = requiredFiles;
        }

        @Override
        public int compareTo(FileInfo otherFileInfo) {
            // TODO сортировка в зависимости от зависимостей :D (типа если зависит, то больше или меньше)
            return 0;
        }
    }
}
