package com.monstrous.controllertest;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controllers;
import de.golfgl.gdx.controllers.mapping.ControllerToInputAdapter;


public class Main extends Game {


    public ControllerToInputAdapter controllerToInputAdapter;

    @Override
    public void create() {


        controllerToInputAdapter = new ControllerToInputAdapter(new MyControllerMappings());
        // bind controller events to keyboard keys
        controllerToInputAdapter.addButtonMapping(MyControllerMappings.BUTTON_FIRE, Input.Keys.ENTER);
        controllerToInputAdapter.addAxisMapping(MyControllerMappings.AXIS_VERTICAL, Input.Keys.UP, Input.Keys.DOWN);
        Controllers.addListener(controllerToInputAdapter);

        setScreen( new MenuScreen( this ) );
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
