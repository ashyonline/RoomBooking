package com.codingbad.roombooking.utils;

import android.view.View;

import java.lang.reflect.Field;

import roboguice.inject.InjectView;

/**
 * Created by ayi on 11/18/15.
 */
public class ViewUtils {
    /**
     * Method that allows Roboguice 2.0 to injectViews inside CustomViews. This is a workaround for
     * Roboguice.injectMember().
     */
    public static void reallyInjectViews(View view) {
        final Field[] fields = view.getClass().getDeclaredFields();
        for (final Field field : fields) {
            final InjectView annotation = field.getAnnotation(InjectView.class);
            if (annotation != null) {
                final int id = annotation.value();
                final View findViewById = view.findViewById(id);
                if (findViewById != null) {
                    field.setAccessible(true);
                    try {
                        field.set(view, findViewById);
                    } catch (final IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (final IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
