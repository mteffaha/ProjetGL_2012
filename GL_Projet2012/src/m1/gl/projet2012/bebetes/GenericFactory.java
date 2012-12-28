package m1.gl.projet2012.bebetes;


import m1.gl.projet2012.exceptions.NoBaseClassFound;

import java.awt.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * General Factory, a Factory design pattern implementation that takes a parend class and allows the instanciation of
 * it's child classes.
 * this class uses a singleton patten to ensure that a unique instance is running at a giving time
 */

public class GenericFactory {
    private static GenericFactory instance;
    private Map<String, Class<?>> listClasses;
    private static Class<?> baseClass = null;

    /**
     * @return the unique instance of GenericFactory
     */
    public static GenericFactory getInstance() throws NoBaseClassFound {
        if (GenericFactory.instance == null)
            GenericFactory.instance = new GenericFactory();
        return GenericFactory.instance;
    }

    public static void setBaseClass(Class<?> base) {
        GenericFactory.baseClass = base;
    }

    /**
     * this function uses reflection to find all the available Bebetes permettited in the program
     */
    private GenericFactory() throws NoBaseClassFound {
        if (GenericFactory.baseClass == null)
            throw new NoBaseClassFound();
        listClasses = getAllChildClasses(baseClass);

    }

    // return an iterator on the available class (return the names)
    public Iterator<String> getAllAvailableClasses() {
        return listClasses.keySet().iterator();
    }

    /**
     * creates an instance of the class using the parameters passed
     *
     * @param classToInstanciate the name of the class to instanciate
     * @param parameters         an array of objects to pass as parameters to the constructor , null means no paramters
     * @return the new instance
     */
    public Object instanciate(String classToInstanciate, Object[] parameters) {
        // we fetch the class to instanciate
        Class<?> toInstanciate = listClasses.get(classToInstanciate);
        Object newInstance = null;
        // we try to instanciate the class
        try {
            boolean noFitConstructorFound = true;
            // we get all the constructors
            for (int i = 0; i < toInstanciate.getConstructors().length; i++) {
                if (parameters == null) {      // we check if no parameters were passsed

                    if (toInstanciate.getConstructors()[i].getParameterTypes().length == 0) {
                        noFitConstructorFound = false; // we found a fit constructor
                        newInstance = toInstanciate.newInstance();
                        break;
                    }

                } else {    // if passing some parameters is required
                    // we first check that the current constructor has at least the same number of parameters
                    if (toInstanciate.getConstructors()[i].getParameterTypes().length == parameters.length) {
                       // we check that the types are all the same
                        boolean sameParameters = true;
                        for (int j = 0; j < parameters.length; j++) {
                            // if null we let pass by, and we let the user deal with it
                            if (parameters[j] == null)
                                continue;

                            // if we're dealing with primitve types
                            if (toInstanciate.getConstructors()[i].getParameterTypes()[j].isPrimitive() || parameters[j].getClass().isPrimitive()) {
                               // we convert both of them to their corresponding wrappers
                                Class a = (toInstanciate.getConstructors()[i].getParameterTypes()[j].isPrimitive()) ? getWrapper(toInstanciate.getConstructors()[i].getParameterTypes()[j]) : toInstanciate.getConstructors()[i].getParameterTypes()[j];
                                Class b = (parameters[j].getClass().isPrimitive()) ? getWrapper(parameters[j].getClass()) : parameters[j].getClass();
                                if (!a.equals(b)) {
                                    sameParameters = false;
                                    break;
                                } else{
                                    continue;
                                }
                            }
                            if (!parameters[j].getClass().equals(toInstanciate.getConstructors()[i].getParameterTypes()[j])) {
                                sameParameters = false;
                                break;
                            }
                        }
                        // if the same constructor
                        // we create an instance an exit the constructors loop
                        if (sameParameters) {
                            noFitConstructorFound = false; // we found a fit constructor
                            newInstance = toInstanciate.getConstructors()[i].newInstance(parameters);
                            break;
                        }

                    }

                }
                //
            }
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return newInstance;

    }

    /**
     * get the wrapper for the primitive type passed
     *
     * @param primitive the primitive type to wrapp
     * @return the wrapper
     */
    private Class<?> getWrapper(Class<?> primitive) {

        if (!primitive.isPrimitive())
            return null;
        if (primitive.equals(int.class)) {
            return Integer.class;

        } else if (primitive.equals(float.class)) {
            return Float.class;

        } else if (primitive.equals(long.class)) {
            return Long.class;

        } else if (primitive.equals(double.class)) {
            return Double.class;

        } else if (primitive.equals(char.class)) {
            return Character.class;

        } else if (primitive.equals(short.class)) {
            return Short.class;

        } else if (primitive.equals(byte.class)) {
            return Byte.class;

        } else if (primitive.equals(boolean.class)) {
            return Boolean.class;
        }
        return null;
    }

    /**
     * return a random Instance from the available classes
     *
     * @return
     */
    public Object randomInstance(Object[] parameters) {
        Random rand = new Random();
        return instanciate((String) listClasses.keySet().toArray()[rand.nextInt(listClasses.values().toArray().length - 1)], parameters);
    }

    /**
     * returns all the child classes of the base class
     * the child classes need to be in the the same package or a child package
     *
     * @param base the base class for the search
     * @return an ArrayList<Class<?>> with the child classes found
     */
    private Map<String, Class<?>> getAllChildClasses(Class base) {
        Map<String, Class<?>> classes = new HashMap<String, Class<?>>();

        // we get the package name
        String packageName = base.getName().substring(0, base.getName().lastIndexOf("."));
        String path = packageName.replace(".", "/");
        // we get the path to the package
        String packageSource = getClass().getClassLoader().getResource(path).getFile();
        // we
        File root = new File(packageSource);
        return fetchBebetesFromFolder(root, packageName, classes);
    }

    /**
     * recursive function for fetching the class in a package directory
     *
     * @param rootFolder  the package directory
     * @param packageName the name of the packagee
     * @param list        the map to store the class name as key and the class as value
     * @return the list filled with the classes found
     */
    private Map<String, Class<?>> fetchBebetesFromFolder(File rootFolder, String packageName, Map<String, Class<?>> list) {

        File[] resources = rootFolder.listFiles();
        // for each class file
        for (int i = 0; i < resources.length; i++) {
            Class<?> bebeteClass = null;
            // if not a class file
            if (!resources[i].getName().endsWith(".class"))
                continue;
            if (resources[i].isDirectory()) {
                list = fetchBebetesFromFolder(resources[i], packageName + "." + resources[i].getName(), list);
            }
            // we load the class
            try {
                bebeteClass = Class.forName(packageName + "." + resources[i].getName().substring(0, resources[i].getName().lastIndexOf(".class")));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            if (bebeteClass.getSuperclass().equals(Bebete.class)) {
                list.put(bebeteClass.getName().substring(bebeteClass.getName().lastIndexOf(".")), bebeteClass);
            }
        }
        return list;
    }

    // TODO : Delete this!!
    public static void main(String[] args) {
        GenericFactory factory = null;
        GenericFactory.setBaseClass(Bebete.class);
        try {
            factory = GenericFactory.getInstance();
        } catch (NoBaseClassFound noBaseClassFound) {
            noBaseClassFound.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //(ChampDeBebetes c, int x, int y, float dC, float vC, Color col)
        Object[] parameters = new Object[]{null, 0, 0, 0.0f, 0.0f, Color.black};
        Bebete bebete = (Bebete) factory.randomInstance(parameters);
        if (bebete == null) {
            System.out.println("returned null");
        }
        System.out.println("Couleur:" + bebete.getCouleur());

    }
}
