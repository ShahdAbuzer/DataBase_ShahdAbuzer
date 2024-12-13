package shahdabuzer;

class Permissions {
    boolean permission = false;
     
    
    public boolean checkPermission(String user_account) {
        if (user_account == "shahd") {
            return permission=true;
        } else {
           return permission=false;
        }
    }

}