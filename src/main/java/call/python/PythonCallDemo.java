package call.python;

import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

public class PythonCallDemo {
    public static void main(String[] args) {
        //首先调用python的解释器
        PythonInterpreter interpreter = new PythonInterpreter();
        //选择执行的的Python语句
        interpreter.exec("print (\"hello java调用python\")");
        //System.out.println(PythonCallDemo.class.getResource("/"));
        interpreter.execfile("D:\\kyf\\my-workspace\\java_demo\\src\\main" +
                "\\java\\call\\python\\pythonScript\\demo.py");

        PyFunction pyFunction = interpreter.get("add", PyFunction.class);
        PyObject pyObj = pyFunction.__call__(new PyInteger(1), new PyInteger(2));
        System.out.println(pyObj.toString());
    }
}
