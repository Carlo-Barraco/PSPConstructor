TARGET = template
OBJS = GameApp.o GameLauncher.o 

BUILD_PRX = 1
PSP_FW_VERSION=371

CFLAGS = -O2 -G0 -Wall -DDEVHOOK -DPSPFW3XX
CXXFLAGS = $(CFLAGS) -fno-exceptions -fno-rtti
ASFLAGS = $(CXXFLAGS)

INCDIR = 
LIBDIR = 
LDFLAGS =
LIBS = -ljge300 -lfreetype -ljpeg -lgif -lpng -lz -lm -lmikmod -lpsppower -lpspmpeg -lpspaudiocodec -lpspaudiolib -lpspaudio -lpspgum -lpspgu -lpsprtc -lstdc++ 

EXTRA_TARGETS = EBOOT.PBP
PSP_EBOOT_TITLE = template_desc
#PSP_EBOOT_ICON = icon.png

PSPSDK=$(shell psp-config --pspsdk-path)
include $(PSPSDK)/lib/build.mak
