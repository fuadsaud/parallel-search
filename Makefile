JAVAC     = javac
SOURCES   = $(wildcard  src/main/java/**/**.java)
SRCDIR    = src/main/java
OUTDIR    = bin/
MAINFILE  = $(SRCDIR)/Search.java
INPUTFILES = ../test_inputs/whatever,../test_inputs/numeros
PATTERNS  = cinco,mé,grazadeus,tres,know

run: classes
	cd bin; java Search $(INPUTFILES) $(PATTERNS)
classes: clean outdir
	$(JAVAC) -d $(OUTDIR) -sourcepath $(SRCDIR) -Xlint $(MAINFILE)
outdir:
	mkdir $(OUTDIR)
clean:
	rm -rf $(OUTDIR)
