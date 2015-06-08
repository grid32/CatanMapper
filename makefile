SRCDIR=src
BINDIR=bin
JAVASRCS=	EvenMap.java Map.java Test.java Tile.java TileRow.java TileTest.java TileWindow.java

java:
	mkdir -p $(BINDIR)
	javac -d $(BINDIR)/ $(addprefix $(SRCDIR)/, $(JAVASRCS))

run:
	make java
	java -classpath "bin/:res/" Test 5 5
	
clean:
	rm $(BINDIR)/*.class
	rm $(SRCDIR)/*~
