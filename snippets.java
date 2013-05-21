//DETECT HOT SWAPPED AREECOMPONENTS    
    
    //experiment with JCL library (needs web.xml and jcl.xml, see WEB-INF)
    public String refreshJCL() throws Exception{
        
        JarClassLoader jcl =  JclContext.get("jcl2");
        JclObjectFactory factory = JclObjectFactory.getInstance();
        
        //Create object of loaded class
        AreeReasoner reasoner = (AreeReasoner) factory.create(jcl, "com.johndoe.areesqlitetools.SQLiteReadOnlyReasoner");
        
//        AreeArguments aargs = new AreeArguments();
//        aargs.put("dbname", "Chemistry");
//        reasoner.setup(aargs);
        ResultSet rs = (ResultSet) reasoner.process(new AreeArguments(), "select identifier from molecules");
        
        ResultSettoJSONOutput output = new ResultSettoJSONOutput();
        return output.process(new AreeArguments(), rs).toString();
    }

    public void refreshClassLoader() throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception{
        ClassLoader parentClassLoader = AreeClassLoader.class.getClassLoader();
        AreeClassLoader classLoader = new AreeClassLoader(parentClassLoader);
        Class myObjectClass = classLoader.loadClass("com/johndoe/areesqlitetools/SQLiteReadOnlyReasoner");
        
        AreeReasoner reasoner = (AreeReasoner) myObjectClass.newInstance();
        AreeArguments aargs = new AreeArguments();
        aargs.put("dbname", "Chemistry");
        reasoner.setup(aargs);
        ResultSet rs = (ResultSet) reasoner.process(new AreeArguments(), "select identifier from molecules");
        
        InitialContext ic = new InitialContext();
        AreeOutput output = (AreeOutput) ic.lookup("java:comp/env/resultsettojsonoutput");
        System.out.println("Return from EJB:" + output.process(new AreeArguments(), rs));
    }
    
       
    
    public void refreshEJB() throws NamingException{
       try {
           Properties props = new Properties(); 
           props.setProperty("java.naming.provider.url", "http://localhost:8080/Aree/Components");
           
            InitialContext ic = new InitialContext(props);
            AreeInput input = (AreeInput) ic.lookup("java:comp/env/plaininput");
            
            AreeReasoner reasoner = (AreeReasoner) ic.lookup("java:global/Aree/sqlitereadonlyreasoner");
            AreeArguments aargs = new AreeArguments();
            aargs.put("dbname", "Chemistry");
            reasoner.setup(aargs);
            
            ResultSet rs = (ResultSet) reasoner.process(new AreeArguments(), "select identifier from molecules");
            
            AreeOutput output = (AreeOutput) ic.lookup("java:comp/env/resultsettojsonoutput");
            
            System.out.println("Return from EJB:" + output.process(new AreeArguments(), rs));
        } catch (NamingException e) {
            throw new EJBException(e);
        } catch (Exception ex) {
            Logger.getLogger(AreeConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }