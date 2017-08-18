package report.models.VFS;



import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;


public class VFSImpl implements VFS {

    private Path rootPath ;

    public VFSImpl(String first, String ... more){
        this.rootPath = Paths.get(first, more);
    }

    @Override
    public boolean isExist() {
        return rootPath.toFile().exists();
    }

    @Override
    public boolean isDirectory() {
        return rootPath.toFile().isDirectory();
    }

    @Override
    public Path getAbsolutePath() {
        return rootPath.toAbsolutePath();
    }

    @Override
    public File toFile() {
        return rootPath.toFile();
    }


//    @Override
//    public FileInputStream getFileInputStream(){
//        if(this.isExist() && !this.isDirectory())
//            try(FileInputStream fis  = new FileInputStream(rootPath.toFile())) {
//                return fis;
//            } catch (IOException ex) {
//                Logger.getLogger(VFSImpl.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        return null;
//    }
//
//    @Override
//    public FileOutputStream getFileOutputStream(){
//        if(this.isExist() && !this.isDirectory())
//            try(FileOutputStream fos =  new FileOutputStream(rootPath.toFile())) {
//                return fos;
//            } catch (IOException ex) {
//                Logger.getLogger(VFSImpl.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        return null;
//    }
}
