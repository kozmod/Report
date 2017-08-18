package report.entities.items.variable;


import report.models.VFS.VFSImpl;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

interface PropertiesFile {

     static Properties get(String first, String ... more){
        VFSImpl vfs = new VFSImpl(first,more);
        Properties prop = new Properties();
        if(vfs.isExist() && !vfs.isDirectory())
            try(FileInputStream fis  = new FileInputStream(vfs.toFile())) {
                prop.load(fis);
            } catch (IOException ex) {
                Logger.getLogger(VFSImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        return prop;
    }

     static void update(Properties property ,String first, String ... more){
        VFSImpl vfs = new VFSImpl(first,more);
        if(vfs.isExist() && !vfs.isDirectory())
            try(FileOutputStream fos =  new FileOutputStream(vfs.toFile());
                BufferedOutputStream bos = new BufferedOutputStream(fos)
            ) {

                property.store(fos, null);
            } catch (IOException ex) {
                Logger.getLogger(VFSImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

}
