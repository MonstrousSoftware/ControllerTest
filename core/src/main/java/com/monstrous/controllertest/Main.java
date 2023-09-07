package com.monstrous.controllertest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerMapping;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.golfgl.gdx.controllers.ControllerMenuStage;
import de.golfgl.gdx.controllers.mapping.ConfiguredInput;
import de.golfgl.gdx.controllers.mapping.ControllerMappings;
import de.golfgl.gdx.controllers.mapping.ControllerToInputAdapter;


public class Main extends ApplicationAdapter {

    public static final int BUTTON_FIRE = 0;
    public static final int AXIS_VERTICAL = 1;

    private Viewport viewport;
    private Skin skin;
    private ControllerMenuStage stage;      // from gdx-controllers-utils, drop in replacement for Stage


    public static class MyControllerMappings extends ControllerMappings {


        public MyControllerMappings() {
            super();
            addConfiguredInput(new ConfiguredInput(ConfiguredInput.Type.button, BUTTON_FIRE));
            addConfiguredInput(new ConfiguredInput(ConfiguredInput.Type.axisDigital, AXIS_VERTICAL));
            commitConfig();
        }

        @Override
        public boolean getDefaultMapping(MappedInputs defaultMapping, Controller controller) {
            ControllerMapping controllerMapping = controller.getMapping();

            defaultMapping.putMapping(new MappedInput(AXIS_VERTICAL, new ControllerAxis(controllerMapping.axisLeftY)));
            defaultMapping.putMapping(new MappedInput(BUTTON_FIRE, new ControllerButton(controllerMapping.buttonA)));
            return true;
        }
    }

    @Override
    public void create() {
        viewport = new ScreenViewport();

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        stage = new ControllerMenuStage(new ScreenViewport());
        rebuild();
        Gdx.input.setInputProcessor(stage);

        ControllerToInputAdapter controllerToInputAdapter = new ControllerToInputAdapter(new MyControllerMappings());
        controllerToInputAdapter.addButtonMapping(BUTTON_FIRE, Input.Keys.ENTER);
        controllerToInputAdapter.addAxisMapping(AXIS_VERTICAL, Input.Keys.UP, Input.Keys.DOWN);
        controllerToInputAdapter.setInputProcessor(stage);

        Controllers.addListener(controllerToInputAdapter);
    }

    private void rebuild() {
        stage.clear();

        Table screenTable = new Table();
        screenTable.setFillParent(true);

        TextButton play = new TextButton("Play Game", skin);
        TextButton options = new TextButton("Options", skin);
        TextButton quit = new TextButton("Quit", skin);

        float pad = 10f;
        screenTable.add(play).pad(pad).row();
        screenTable.add(options).pad(pad).row();
        screenTable.add(quit).pad(pad).row();

        screenTable.pack();

        screenTable.setColor(1,1,1,0);                   // set alpha to zero
        screenTable.addAction(Actions.fadeIn(3f));           // fade in
        stage.addActor(screenTable);

        options.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

            }
        });

        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

            }
        });

        quit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.exit();
            }
        });

        // set up for keyboard/controller navigation
        stage.clearFocusableActors();
        stage.addFocusableActor(play);
        stage.addFocusableActor(options);
        stage.addFocusableActor(quit);
        stage.setFocusedActor(play);
        stage.setEscapeActor(quit);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.1f, 0.2f, 0.3f, 1);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
        rebuild();
    }


    @Override
    public void dispose() {
        // Destroy screen's assets here.
        stage.dispose();
        skin.dispose();
    }


}
