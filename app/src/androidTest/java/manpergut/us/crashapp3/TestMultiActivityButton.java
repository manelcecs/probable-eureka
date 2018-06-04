package manpergut.us.crashapp3;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by manuel on 6/03/18.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
@MediumTest
public class TestMultiActivityButton {

    private static final Logger log = Logger.getLogger(TestMultiActivityButton.class.getName());

    private static final String TAGLOG = "Test1";
    private static final String BASIC_PACKAGE = "manpergut.us.crashapp3";
    private static final long TIMEOUT = 5000;
    private static final String application = "CrashApp3";

    private static UiDevice uiDevice;
    private static final String dir = "./fichero/grafo1.txt";


    private static String TAGFile = "Test - "+application+ " - " +Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+":"+Calendar.getInstance().get(Calendar.MINUTE);

    //No estoy muy seguro
    //Pero algo así como decir a UIAutomator que se debe ejecutar sobre esa clase
    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    //Inicialización del test.
    //Decimos que carge todos los parámetros y se inicie desde el lanzador/escritorio.
    @Before
    public void setUp() throws RemoteException {
        //Inicializa la instacia del dispositivo
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        //Si el dispostivo está bloqueado (sin protección), desbloqueado.
        if(!uiDevice.isScreenOn()){
            int alturaYIni = uiDevice.getDisplayHeight()-100;
            int alturaYFin = 100;
            int anchuraXPantalla = uiDevice.getDisplayWidth()/2;
            uiDevice.wakeUp();//Enciende la pantalla
            //uiDevice.swipe( uiDevice.getDisplayWidth()/2,uiDevice.getDisplayHeight()-100, uiDevice.getDisplayWidth()/2, 100,180);
            uiDevice.swipe( anchuraXPantalla, alturaYIni, anchuraXPantalla, alturaYFin, 180);//Desliza sobre la pantalla para desbloquearla, de abajo arriba; ~1s
        }
        //Sale al launcher
        uiDevice.pressHome();
        //Espera la respuesta del lanzador.
        uiDevice.wait(Until.hasObject(By.pkg(getLauncherPackageName()).depth(0)), TIMEOUT);
        //Lanza la aplicacion
        Context con =InstrumentationRegistry.getContext();
        final Intent inte = con.getPackageManager().getLaunchIntentForPackage(BASIC_PACKAGE);
        //Limpia las instancias/cierra otros procesos de la app
        inte.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //inicia un nuevo contexto/actividad
        con.startActivity(inte);
        //Espera la aparición/inicio de la app
        uiDevice.wait(Until.hasObject(By.pkg(BASIC_PACKAGE).depth(0)), TIMEOUT);

    }

    private String getLauncherPackageName(){
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();

        ResolveInfo res = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return res.activityInfo.packageName;
    }

    @Test
    public void test() throws UiObjectNotFoundException, RemoteException, IOException{

        uiDevice.pressHome();//Salimos al launcher
        log.log(Level.FINE, TAGLOG+": saliendo al launcher.");

        UiObject appLaunch = uiDevice.findObject(new UiSelector().description("Apps"));//Abrimos el cajón de aplicaciones
        UiObject app;
        appLaunch.clickAndWaitForNewWindow(1500);
        log.log(Level.FINE, TAGLOG+": abriendo cajón.");

        UiScrollable appView = new UiScrollable(new UiSelector().scrollable(true));//Setteamos la el cajón en horizontal (depende del dispositivo)
        appView.setAsHorizontalList();
        log.log(Level.FINE, TAGLOG+": setting appBox en hoizontal");
        app = appView.getChildByText(new UiSelector()
                .className(android.widget.TextView.class.getName()), application);
        log.log(Level.FINE, TAGLOG+": buscando la aplicación.");//Buscamos la aplicación a testear
        app.clickAndWaitForNewWindow(1500);//Pulsamos y esperamos a que se inicie.
        log.log(Level.FINE, TAGLOG+": iniciando aplicación.");

        BySelector by = By.clazz("android.widget.Button");//Creamos el filtro del widget que queremos buscar
        List<UiObject2> l = uiDevice.findObjects(by);
        log.log(Level.FINE, TAGLOG+": buscando los botones en la primera vista de la app");//Generamos la lista de objetos que hemos encontrado en la primera pantalla

        if(l.size()>0){//Si en la primera vista hay algún objeto:
            buscaBotonesArbol(l, 0, application);//Función recursiva
        }
    }

    /**
     * Función que recibe la lista de botones encontrados en la vista,
     * el número (n) de botones que se han pulsado,
     * nombre de la vista que llama a la función (vértice del grafo).
     * */
    private void buscaBotonesArbol(List< UiObject2 > l, int n, String parent) throws
            UiObjectNotFoundException , IOException{
        UtilFichero.escribeFichero("grafo"+TAGFile+".txt", parent+" -id:"+n+" => [");//Escribe en un fichero nuevo en el dispositivo
        log.log(Level.FINE, TAGLOG+": escribiendo en fichero entrada.");

        for(UiObject2 o : l){//Recorremos la lista de objetos
            n++;//aumentamos el número de botones recorridos/pulsados
            try {
                String t = o.getText();//Texto del botón
                log.log(Level.FINE, TAGLOG+": localizando botón.");
                boolean r = o.clickAndWait(Until.newWindow(), TIMEOUT / 2);//Pulsamos el botón, si se genera una nueva ventana, r es true
                log.log(Level.FINE, TAGLOG+": botón pulsado");
                if (r) {//Si se genera una nueva ventana
                    log.log(Level.FINE, TAGLOG + ": accediendo a la nueva ventana.");

                    UtilFichero.escribeFichero("grafo" + TAGFile + ".txt", t + " - id:" + n + "]\n");//escribimos el nuevo vértice generado
                    List<UiObject2> l1 = uiDevice.findObjects(By.clazz("android.widget.Button"));//Generamos la nueva lista de widgets (insert en orden de aparición en pantalla)

                    log.log(Level.FINE, TAGLOG + ": generando lista de la nueva vista.");
                    buscaBotonesArbol(l1, 0, t);//Función recursiva con la nueva lista, el número de botones y el vértice generado.
                }
            }catch(Exception e) {
                //e.printStackTrace();
                UtilFichero.escribeFichero("grafo" + TAGFile + ".txt", e.getMessage() + "]\n");
            }
        }
        uiDevice.pressBack();//Vuelta atrás

    }


    @After
    public void terminaTest() throws RemoteException{
        //Al terminar el test, sale de la aplicación
        uiDevice.pressHome();
        uiDevice.wait(Until.hasObject(By.pkg(getLauncherPackageName()).depth(0)), TIMEOUT);
        //Bloquea el terminal
        uiDevice.sleep();
    }

}
