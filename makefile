SRCDIR=src
BINDIR=bin
JAVASRCS=	Map.java Test.java Tile.java TileRow.java TileTest.java TileWindow.java

java:
	javac -d $(BINDIR)/ $(addprefix $(SRCDIR)/, $(JAVASRCS))

run:
	make java
	java -classpath "bin/" Test 5 5
	
clean:
	rm $(BINDIR)/*.class
	rm $(SRCDIR)/*~
