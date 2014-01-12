package com.example.iwrite;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.debug.Debug;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;
import android.view.Display;

public class MainActivity extends SimpleBaseGameActivity implements IOnSceneTouchListener
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
	public static DrawImage [] whiteChalk = new DrawImage[5000];
	
	public static Sprite backGround, blackBoard, moOutLine;
	
	public static float moOutLineX, moOutLineY;
	
	public static int i, j;
	public static String DEBUG_TAG = MainActivity.class.getSimpleName();
	public static int aCounter = 0, serialCounter = 1;
	
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
		mBitmapTextureAtlasWhiteChalk.load();
		
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
	
		mScene.setOnSceneTouchListener(this);
		vertexBufferObjectManager = getVertexBufferObjectManager();
		serialCounter = 1;

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
		
		for(i=1; i<=7; i++)
		{	
			numberSprites[i] = new NumberSprites(moOutLineX+8*i, moOutLineY+50*i-160, 
				mTextureRegionNumber[i], getVertexBufferObjectManager());
		
			mScene.attachChild(numberSprites[i]);
			mScene.registerTouchArea(numberSprites[i]);
			numberSprites[i].setScale((float) 0.3);
			numberSprites[i].setVisible(false);
		
		}
		
		MainActivity.mScene.registerUpdateHandler(new TimerHandler((float)1, new ITimerCallback() 
		{
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) 
			{
				// TODO Auto-generated method stub
				Animation.scale(1, numberSprites[1]);
				i=1;
				//Animation.shake(1, numberSprites[1], 10);
			} 
		}));
		
		MainActivity.mScene.registerUpdateHandler(new TimerHandler((float)0.07, true, new ITimerCallback() 
		{
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) 
			{
				// TODO Auto-generated method stub
				
				if(numberSprites[serialCounter]!= null && whiteChalk[aCounter]!= null)
				{
					//Debug.d("a");
					//Debug.d("whiteChalk[aCounter]:"+whiteChalk[aCounter].getX());
					//Debug.d("numberSprites[serialCounter].getX():"+numberSprites[serialCounter].getX());
				if(distance(whiteChalk[aCounter], numberSprites[serialCounter]))
				{
					//Debug.d("b");
					for(int k=0; k<aCounter; k++)
						{
							//Debug.d("c");
							//whiteChalk[k].setVisible(false);
							mScene.detachChild(whiteChalk[k]);
							//aCounter=0;
						}
				}
				}
				
//				if(aCounter> 135)
//				{
//					for(int k=0; k<135; k++)
//					{
//						//whiteChalk[k].setVisible(false);
//						mScene.detachChild(whiteChalk[k]);
//						aCounter=0;
//					} 
//				}
			} 
		}));
		
		return mScene;
	}
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) 
	{
		// TODO Auto-generated method stub
		
		if(pSceneTouchEvent.isActionDown())
		{
			//Debug.d("action down");
			return true;
		}
		
		if(pSceneTouchEvent.isActionMove())
		{
			//Debug.d("action move");
			aCounter++;
			whiteChalk[aCounter] = new DrawImage(pSceneTouchEvent.getX(), pSceneTouchEvent.getY(), mWhiteChalkTextureRegion, getVertexBufferObjectManager());
			mScene.attachChild(MainActivity.whiteChalk[aCounter]);
			//whiteChalk.setScale((float) 0.4);
			//Debug.d("I:"+aCounter); 
			
			if(whiteChalk[aCounter].collidesWith(numberSprites[serialCounter]))
			{
				mScene.detachChild(numberSprites[serialCounter]);
				serialCounter++;
			}
			
			
//			for(int b=1; b<=7; b++)
//			{
//				if(numberSprites[b].collidesWith(whiteChalk[aCounter]))
//				{
//					mScene.detachChild(numberSprites[b]);
//					Debug.d("collided:"+b);
//				}
//			}
			
			return true;
		}
		
		if(pSceneTouchEvent.isActionUp())
		{
			//Debug.d("action up");
			return true;
		}
		
		return true;
	}
	
	public static boolean distance(Sprite a , Sprite b)
	{
		double dist = Math.sqrt(Math.pow((b.getX() - a.getX()), 2) + Math.pow((b.getY() - a.getY()), 2));
		
		if(dist>200)
		{
			return true;
		}
		else
			return false;
	}
}
