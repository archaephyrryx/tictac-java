all:

.SUFFIXES : .java .class

.java.class: 
	javac $<

clean:
	$(RM) *.class
