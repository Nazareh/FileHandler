/**
 * 
 * Class to find files within a folder and its subfolder, 
 * according to a name pattern;
 * 
 * Files that match the pattern, if there is one, are added to
 * a List, which is returned by the getMatchedFiles method;
 * 
 */
 
package filehandler;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.CONTINUE;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nazareh, created on 30-JAN-2018
 */
public class FindFiles extends SimpleFileVisitor<Path>{
        private PathMatcher matcher;
        private int matchesCounter = 0;
        private List<Path> matchedFiles = new ArrayList();
        
        FindFiles() {
        }
        FindFiles(String pattern) {
            matcher = FileSystems.getDefault()
                    .getPathMatcher("glob:" + pattern);
            
        }
        public void restartMatchesCounter (){
            matchesCounter = 0;
        }
         //setters 
        public int getMatchesCounter( ) {
             return matchesCounter;
        }
        public List<Path> getMatchedFiles(){
            return matchedFiles;
        }
        // Compares the glob pattern against
        // the file or directory name.
        void find(Path file) throws IOException {
            Path name = file.getFileName();
            if (name != null && (matcher.matches(name))) {
                matchesCounter++;
                matchedFiles.add(file);
            }
        }
        // Invoke the pattern matching
        // method on each file.
        @Override
        public FileVisitResult visitFile(Path file,
                BasicFileAttributes attrs) throws IOException {
            find(file);
            return CONTINUE;
        }
        // Invoke the pattern matching
        // method on each directory.
        @Override
        public FileVisitResult preVisitDirectory(Path dir,BasicFileAttributes attrs) throws IOException {
            find(dir);
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file,
                IOException exc) {
            System.err.println(exc);
            return CONTINUE;
        }
}
