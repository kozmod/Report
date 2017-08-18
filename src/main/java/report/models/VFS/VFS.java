package report.models.VFS;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;

interface  VFS {

    boolean isExist();
    boolean isDirectory();
    Path getAbsolutePath();
    File toFile();


}
