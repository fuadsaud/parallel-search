JAVAC     = javac
SOURCES   = $(wildcard  src/main/java/**/**.java)
SRCDIR    = src/main/java
OUTDIR    = bin/
MAINFILE  = $(SRCDIR)/im/fuad/rit/concpar/p1/P1.java
INPUTFILE = ../input

run: classes
	cd bin; java im.fuad.rit.concpar.p1.P1 $(INPUTFILE)
classes: clean outdir
	$(JAVAC) -d $(OUTDIR) -sourcepath $(SRCDIR) -Xlint:unchecked $(MAINFILE)
outdir:
	mkdir $(OUTDIR)
clean:
	rm -rf $(OUTDIR)
