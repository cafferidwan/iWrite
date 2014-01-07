package com.example.iwrite;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;

import android.view.Display;
import android.widget.Toast;

public class MainActivity extends SimpleBaseGameActivity 
{

	static int CAMERA_WIDTH;
	static int CAMERA_HEIGHT; 
	public Camera mCamera;   
	public static Scene mScene;
	public static VertexBufferObjectManager vertexBufferObjectManager;
	public static MainActivity MainActivityInstace;

	public static BitmapTextureAtlas mBitmapTextureAtlasBlackBoard,
	 mBitmapTextureAtlasMoOutLine, mBitmapTextureAtlasBackGround,
	 mBitmapTextureAtlasWhiteChalk, mBitmapTextureAtlasStar;
	
	public static ITextureRegion mbackGroundTextureRegion, mBlackBoardTextureRegion, 
	mMoOutLineTextureRegion, mWhiteChalkTextureRegion, mStarTextureRegion;
	
	public static BitmapTextureAtlas [] mBitmapTextureAtlasNumber = new BitmapTextureAtlas[25];
	public static ITextureRegion [] mTextureRegionNumber = new ITextureRegion[25];
	public static NumberSprites[] numberSprites= new NumberSprites[25];
	
	public static Sprite backGround, blackBoard, moOutLine;
	
	public static float moOutLineX, moOutLineY;
	
	@Override
	public EngineOptions onCreateEngineOptions() 
	{
		// TODO Auto-generated method stub
		MainActivityInstace = this;
		Display display = getWindowManager().getDefaultDisplay();
		CAMERA_HEIGHT = display.getHeight();
		CAMERA_WIDTH = display.getWidth();

		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
	}

	@Override
	protected void onCreateResources() 
	{
		// TODO Auto-generated method stub
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("iWriteGFX/");
		
		mBitmapTextureAtlasBackGround = new BitmapTextureAtlas(this.getTextureManager(), 1600, 864, TextureOptions.BILINEAR);
		
		mBitmapTextureAtlasBlackBoard = new BitmapTextureAtlas(this.getTextureManager(), 400, 400, TextureOptions.BILINEAR);
		
		mBitmapTextureAtlasMoOutLine = new BitmapTextureAtlas(this.getTextureManager(), 254, 262, TextureOptions.BILINEAR);
		
		mBitmapTextureAtlasWhiteChalk = new BitmapTextureAtlas(this.getTextureManager(), 50, 50, TextureOptions.BILINEAR);
		 
		mBitmapTextureAtlasStar = new BitmapTextureAtlas(this.getTextureManager(), 50, 50, TextureOptions.BILINEAR);
		 
		//All the numbers
		for(int i=1; i<=7; i++)
		{
			mBitmapTextureAtlasNumber[i] = new BitmapTextureAtlas(this.getTextureManager(), 150, 150, TextureOptions.BILINEAR);
		}
		
		mbackGroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlasBackGround, this,
				"JungleBG.png", 0, 0,  1, 1); 
		
		mBlackBoardTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlasBlackBoard, this,
				"blackboard.png", 0, 0,  1, 1); 
				
		mMoOutLineTextureRegion =  BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlasMoOutLine, this,
				"moOutlineCrop.png", 0, 0,  1, 1); 
				
		mWhiteChalkTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlasWhiteChalk, this,
				"chalk2.png", 0, 0,  1, 1);
		
		mStarTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlasStar, this,
				"star.png", 0, 0,  1, 1);
		
		//All the numbers
		for(int i=1; i<=7; i++)
		{
			mTextureRegionNumber[i] = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mBitmapTextureAtlasNumber[i], this,
					"Number"+i+".png", 0, 0,  1, 1);
		}
		
		mBitmapTextureAtlasBackGround.load();
		mBitmapTextureAtlasBlackBoard.load();
		mBitmapTextureAtlasMoOutLine.load();
		mBitmapTextureAtlasWhiteChalk.load();
		mBitmapTextureAtlasStar.load();
		
		//All the numbers
		for(int i=1; i<=7; i++)
		{
			mBitmapTextureAtlasNumber[i].load();
		}
		
	}

	@Override
	protected Scene onCreateScene()
	{
		// TODO Auto-generated method stub
		
		mScene = new Scene();
		mScene.setBackground(new Background(Color.WHITE));
	
		vertexBufferObjectManager = getVertexBufferObjectManager();

		backGround = new Sprite(0, 0, mbackGroundTextureRegion,
				getVertexBufferObjectManager());
		backGround.setHeight(CAMERA_HEIGHT);
		backGround.setWidth(CAMERA_WIDTH);
		mScene.attachChild(backGround);

		moOutLineX = CAMERA_WIDTH / 2 - 130;
		moOutLineY = CAMERA_HEIGHT / 2 - 130;
		
		blackBoard = new Sprite(moOutLineX-160, moOutLineY-85, mBlackBoardTextureRegion,
				getVertexBufferObjectManager());
		blackBoard.setHeight((float) (blackBoard.getHeight()*1.7));
		blackBoard.setWidth((float) (blackBoard.getWidth()*1.5));
		mScene.attachChild(blackBoard);

		moOutLine = new Sprite(moOutLineX, moOutLineY, mMoOutLineTextureRegion,
				getVertexBufferObjectManager());
		mScene.attachChild(moOutLine);
		

//		LoopEntityModifier EntityModifier =
//                new LoopEntityModifier( new RotationModifier(8, 0, 360) );
		
		
		for(int i=1; i<=7; i++)
		{	
			numberSprites[i] = new NumberSprites(moOutLineX+8*i, moOutLineY+30*i-60, mTextureRegionNumber[i], getVertexBufferObjectManager());
			mScene.attachChild(numberSprites[i]);
			mScene.registerTouchArea(numberSprites[i]);
//			numberSprites[i].registerEntityModifier(EntityModifier);
			numberSprites[i].setScale((float) 0.3);
		}
			
		return mScene;
	}

}
