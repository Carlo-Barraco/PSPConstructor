#include "GameApp.h"

vector<JTexture*> sprTexs;
vector<JTexture*> bgTexs;
vector<JQuad*> bgQuads;
vector<JMusic*> sounds;
vector<hgeFont*> fonts;
vector<hgeParticleSystem*> particles;
vector<object0*> object0s[1];
int WorldIndex = 0;

object0::object0(int x, int y,JTexture* tex)
{
X = x;
Y = y;
SpriteTex = tex;
Angle=0;
XScale=1;
YScale=1;
SpriteQuad = new JQuad(SpriteTex, 0, 0,256,256);
SpriteQuad->SetHotSpot(128,128);
}
void object0::Create()
{
}
void object0::Destroy()
{
}
void object0::Update()
{
X += HSpeed;
Y += VSpeed;
for (int j=0;j<pparticles.size();j++)
{
pparticles.at(j)->Update(JGE::GetInstance()->GetDelta());
}
}
void object0::Render()
{
for (int j=0;j<pparticles.size();j++)
{
pparticles.at(j)->Render();
}
JRenderer::GetInstance()->RenderQuad(SpriteQuad,X,Y,Angle,XScale,YScale);
}
void object0::Pause()
{
}
void object0::Resume()
{
}
GameApp::GameApp()
{
sprTexs.push_back(JRenderer::GetInstance()->LoadTexture("sprite0.png",TEX_TYPE_USE_VRAM));
object0s[0].push_back(new object0(160,112,sprTexs.at(0)));
}

GameApp::~GameApp()
{
}

void GameApp::Create()
{
JRenderer::GetInstance()->EnableVSync(true);
for (int i=0;i<1;i++)
{
for (int j=0;j<object0s[i].size();j++)
{
object0s[i].at(j)->Create();
}
}
}

void GameApp::Destroy()
{
for (int i=0;i<1;i++)
{
for (int j=0;j<object0s[i].size();j++)
{
object0s[i].at(j)->Destroy();
}
}
}

void GameApp::Update()
{
for (int j=0;j<object0s[WorldIndex].size();j++)
{
object0s[WorldIndex].at(j)->Update();
}
}

void GameApp::Render()
{
JRenderer* renderer = JRenderer::GetInstance();
renderer->ClearScreen(ARGB(0,0,0,0));
for (int j=0;j<object0s[WorldIndex].size();j++)
{
object0s[WorldIndex].at(j)->Render();
}
}

void GameApp::Pause()
{
for (int j=0;j<object0s[WorldIndex].size();j++)
{
object0s[WorldIndex].at(j)->Pause();
}
}

void GameApp::Resume()
{
for (int j=0;j<object0s[WorldIndex].size();j++)
{
object0s[WorldIndex].at(j)->Resume();
}
}