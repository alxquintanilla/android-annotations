package alxquintanilla.annotations;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Implements annotations in runtime.
 */
public class ConfigureAnnotations {

    /**
     * Send the activity using (this)
     * @param activity
     */
    public static void configure(final Activity activity) {
        final Class aClass = activity.getClass();

        try {

            ActivityView classAnnotation = (ActivityView) aClass.getAnnotation(ActivityView.class);

            if(classAnnotation!=null){
                activity.setContentView(classAnnotation.value());
            }

            Field[] fields = aClass.getDeclaredFields();
            /*
                Field section, evaluates each class field and inject values/resources
             */
            for(Field field :fields){
                Inject e = field.getAnnotation(Inject.class);

                if(e!=null){
                    if(e.clazz() == TextView.class){
                        field.setAccessible(true);
                        field.set(activity, (TextView) activity.findViewById(e.id()));
                    }

                    if(e.clazz() == Button.class){
                        field.setAccessible(true);
                        field.set(activity, (Button) activity.findViewById(e.id()));
                    }

                    if(e.clazz() == View.class){
                        field.setAccessible(true);
                        field.set(activity, (View) activity.findViewById(e.id()));
                    }

                    if(e.clazz() == LinearLayout.class){
                        field.setAccessible(true);
                        field.set(activity, (LinearLayout) activity.findViewById(e.id()));
                    }

                }

            }

            Method[] methods = aClass.getDeclaredMethods();
            /*
                method section, evaluates each class method and implements methods/events
             */

            for(Method m: methods){
                Init init = m.getAnnotation(Init.class);

                if(init != null){
                    m.setAccessible(true);
                    m.invoke(activity, null);
                }

                OnClick click = m.getAnnotation(OnClick.class);
                final Method cpMethod = m;

                if(click!=null){
                    activity.findViewById(click.idView()).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cpMethod.setAccessible(true);
                            try {
                                cpMethod.invoke(activity, v);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

            }

        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }
}
