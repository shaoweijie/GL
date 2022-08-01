package com.hancher.common.utils.java;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ColorUtil {
    /**
     *     <!--material 颜色组-->
     *     <color name="material_red">#f44336</color>
     *     <color name="material_pink">#E91E63</color>
     *     <color name="material_purple">#9C27B0</color>
     *     <color name="material_deep_purple">#673AB7</color>
     *     <color name="material_indigo">#3F51B5</color>
     *     <color name="material_blue">#2196F3</color>
     *     <color name="material_light_blue">#03A9F4</color>
     *     <color name="material_cyan">#00BCD4</color>
     *     <color name="material_teal">#009688</color>
     *     <color name="material_green">#4CAF50</color>
     *     <color name="material_light_green">#8BC34A</color>
     *     <color name="material_lime">#CDDC39</color>
     *     <color name="material_yellow">#FFEB3B</color>
     *     <color name="material_amber">#FFC107</color>
     *     <color name="material_orange">#FF9800</color>
     *     <color name="material_deep_orange">#FF5722</color>
     *     <color name="material_brown">#795548</color>
     *     <color name="material_grey">#9E9E9E</color>
     *     <color name="material_blue_grey">#607D8B</color>
     */
    public static List<Integer> getMaterialColors(){
        ArrayList<Integer> data = new ArrayList<>();
        data.add(Color.parseColor("#f44336"));
        data.add(Color.parseColor("#E91E63"));
        data.add(Color.parseColor("#9C27B0"));
        data.add(Color.parseColor("#673AB7"));
        data.add(Color.parseColor("#3F51B5"));
        data.add(Color.parseColor("#2196F3"));
        data.add(Color.parseColor("#03A9F4"));
        data.add(Color.parseColor("#00BCD4"));
        data.add(Color.parseColor("#009688"));
        data.add(Color.parseColor("#4CAF50"));
        data.add(Color.parseColor("#8BC34A"));
        data.add(Color.parseColor("#CDDC39"));
        data.add(Color.parseColor("#FFEB3B"));
        data.add(Color.parseColor("#FFC107"));
        data.add(Color.parseColor("#FF9800"));
        data.add(Color.parseColor("#FF5722"));
        data.add(Color.parseColor("#795548"));
        data.add(Color.parseColor("#9E9E9E"));
        data.add(Color.parseColor("#607D8B"));
        return data;
    }

    public static int getRandomColor(){
        List<Integer> colors = getMaterialColors();
        return colors.get(new Random().nextInt(colors.size()));
    }
}
