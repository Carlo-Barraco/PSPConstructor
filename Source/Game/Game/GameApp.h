#ifndef _GAMEAPP_H_
#define _GAMEAPP_H_
#include <stdio.h>
#include <vector>
#include <JGE.h>
#include <JRenderer.h>
#include <JApp.h>
#include <JSoundSystem.h>
#include <hge/hgeparticle.h>
#include <hge/hgefont.h>
#include <JLBFont.h>

using namespace std;

class GameApp:public JApp
{
public:
GameApp();
virtual ~GameApp();
virtual void Create();
virtual void Destroy();
virtual void Update();
virtual void Render();
virtual void Pause();
virtual void Resume();
};
class object0
{
public:
float X,Y,Angle,XScale,YScale,HSpeed,VSpeed;
JTexture* SpriteTex;
JQuad* SpriteQuad;
vector<hgeParticleSystem*> pparticles;
object0(int x, int y,JTexture* tex);
void Create();
void Destroy();
void Update();
void Render();
void Pause();
void Resume();
};

#endif