#include <Xm/Xm.h>
#include <GL/GLwMDrawA.h>

void f(Widget parent)
{
  Arg args[12];
  int n=0;

  XtSetArg(args[n], XtNwidth, 100); n++;
  
  Widget glx = XtCreateWidget("paletteGLX", glwMDrawingAreaWidgetClass, parent, args, n);
}

int main(int argc, char* argv[])
{
  f(0);
  return 0;
}
