package report.models.beck.vfs;

import java.io.File;
import java.nio.file.Path;

interface  VFS {

    boolean isExist();
    boolean isDirectory();
    Path getAbsolutePath();
    File toFile();


}
