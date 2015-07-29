SRCDIR=src
BINDIR=bin
JAVASRCS=	EvenMap.java MainTest.java MainWindow.java Map.java SpreadMap.java Tile.java TileRow.java TileTest.java TileWindow.java

java:
	mkdir -p $(BINDIR)
	javac -d $(BINDIR)/ $(addprefix $(SRCDIR)/, $(JAVASRCS))

test:
	make java
	java -classpath "bin/:res/" Test 5 5

gui:
	make java
	java -classpath "bin/:res/" MainTest

doxygen:
	doxygen doxyfile
	(cd doc/latex && make)
	
clean:
	rm $(BINDIR)/*.class
	rm $(SRCDIR)/*~
