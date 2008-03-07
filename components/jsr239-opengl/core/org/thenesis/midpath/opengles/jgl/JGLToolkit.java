package org.thenesis.midpath.opengles.jgl;

import javax.microedition.khronos.egl.EGL;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.egl.EGLToolkit;

import org.thenesis.midpath.opengles.JavaEGLDisplay;
import org.thenesis.midpath.opengles.JavaEGLSurface;

public class JGLToolkit extends EGLToolkit {
	
	private JavaEGL10 egl = new JavaEGL10();
	
	private EGLContext noContext = new JavaEGLContext();
	private EGLDisplay noDisplay = new JavaEGLDisplay();
	private EGLSurface noSurface = new JavaEGLSurface(null, 0, 0);

	public EGL getEGL() {
		return egl;
	}

	public EGLContext getNoContext() {
		return noContext;
	}

	public EGLDisplay getNoDisplay() {
		return noDisplay;
	}

	public EGLSurface getNoSurface() {
		return noSurface;
	}


}


