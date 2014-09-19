JAVAC     = javac
SOURCES   = $(wildcard  src/main/java/**/**.java)
SRCDIR    = src/main/java
OUTDIR    = bin/
MAINFILE  = $(SRCDIR)/Search.java
INPUTFILES = ../wow,../lol
PATTERNS  = cinco,mé,grazadeus,tres,know

run: classes
	cd bin; java Search $(INPUTFILES) $(PATTERNS)
classes: clean outdir
	$(JAVAC) -d $(OUTDIR) -sourcepath $(SRCDIR) -Xlint:unchecked $(MAINFILE)
outdir:
	mkdir $(OUTDIR)
clean:
	rm -rf $(OUTDIR)
