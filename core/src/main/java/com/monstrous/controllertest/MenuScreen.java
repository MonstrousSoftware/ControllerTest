package com.monstrous.controllertest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
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


public class MenuScreen extends ScreenAdapter {

    private Main game;
    private Viewport viewport;
    private Skin skin;
    private ControllerMenuStage stage;      // from gdx-controllers-utils, drop in replacement for Stage


    public MenuScreen(Main game) {
        this.game = game;
    }

    public void show() {
        viewport = new ScreenViewport();

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        stage = new ControllerMenuStage(new ScreenViewport());
        rebuild();
        Gdx.input.setInputProcessor(stage);
        game.controllerToInputAdapter.setInputProcessor(stage); // forward controller input to stage
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
    public void render(float deltaTime) {
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
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
        stage.dispose();
        skin.dispose();
    }
}
