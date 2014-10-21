package fr.ritaly.graphml4j;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.atomic.AtomicInteger;

import javanet.staxutils.IndentingXMLStreamWriter;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.lang.Validate;

public class GraphMLWriter {

	private final Writer writer;

	private final XMLStreamWriter streamWriter;

	private boolean closed = false;

	private final AtomicInteger nodeSequence = new AtomicInteger();

	public GraphMLWriter(Writer writer) throws GraphMLException {
		Validate.notNull(writer, "The given writer is null");

		try {
			this.writer = writer;
			this.streamWriter = new IndentingXMLStreamWriter(XMLOutputFactory.newFactory().createXMLStreamWriter(writer));
		} catch (XMLStreamException e) {
			throw new GraphMLException(e);
		} catch (FactoryConfigurationError e) {
			throw new GraphMLException(e);
		}
	}

	public void startDocument() throws GraphMLException {
		assertNotClosed();

		try {
			this.streamWriter.writeStartDocument("1.0");

			// Write the <graphml> root tag and the XML namespaces
			this.streamWriter.writeStartElement("graphml");
			this.streamWriter.writeDefaultNamespace("http://graphml.graphdrawing.org/xmlns");
			this.streamWriter.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
			this.streamWriter.writeNamespace("y", "http://www.yworks.com/xml/graphml");
			this.streamWriter.writeNamespace("yed", "http://www.yworks.com/xml/yed/3");
			this.streamWriter.writeAttribute("xsi:schemaLocation", "http://graphml.graphdrawing.org/xmlns http://www.yworks.com/xml/schema/graphml/1.1/ygraphml.xsd");

			// Add the yEd-specific metadata
			this.streamWriter.writeEmptyElement("key");
			this.streamWriter.writeAttribute("for", "graphml");
			this.streamWriter.writeAttribute("id", "d0");
			this.streamWriter.writeAttribute("yfiles.type", "resources");

			this.streamWriter.writeEmptyElement("key");
			this.streamWriter.writeAttribute("for", "port");
			this.streamWriter.writeAttribute("id", "d1");
			this.streamWriter.writeAttribute("yfiles.type", "portgraphics");

			this.streamWriter.writeEmptyElement("key");
			this.streamWriter.writeAttribute("for", "port");
			this.streamWriter.writeAttribute("id", "d2");
			this.streamWriter.writeAttribute("yfiles.type", "portgeometry");

			this.streamWriter.writeEmptyElement("key");
			this.streamWriter.writeAttribute("for", "port");
			this.streamWriter.writeAttribute("id", "d3");
			this.streamWriter.writeAttribute("yfiles.type", "portuserdata");

			// Define the attributes for type 'node'
			this.streamWriter.writeEmptyElement("key");
			this.streamWriter.writeAttribute("attr.name", "url");
			this.streamWriter.writeAttribute("attr.type", "string");
			this.streamWriter.writeAttribute("for", "node");
			this.streamWriter.writeAttribute("id", "d4");

			this.streamWriter.writeEmptyElement("key");
			this.streamWriter.writeAttribute("attr.name", "description");
			this.streamWriter.writeAttribute("attr.type", "string");
			this.streamWriter.writeAttribute("for", "node");
			this.streamWriter.writeAttribute("id", "d5");

			// Define the type 'node'
			this.streamWriter.writeEmptyElement("key");
			this.streamWriter.writeAttribute("for", "node");
			this.streamWriter.writeAttribute("id", "d6");
			this.streamWriter.writeAttribute("yfiles.type", "nodegraphics");

			// Define the attributes for type 'graph'
			this.streamWriter.writeEmptyElement("key");
			this.streamWriter.writeAttribute("attr.name", "Description");
			this.streamWriter.writeAttribute("attr.type", "string");
			this.streamWriter.writeAttribute("for", "graph");
			this.streamWriter.writeAttribute("id", "d7");

			// Define the attributes for type 'edge'
			this.streamWriter.writeEmptyElement("key");
			this.streamWriter.writeAttribute("attr.name", "url");
			this.streamWriter.writeAttribute("attr.type", "string");
			this.streamWriter.writeAttribute("for", "edge");
			this.streamWriter.writeAttribute("id", "d8");

			this.streamWriter.writeEmptyElement("key");
			this.streamWriter.writeAttribute("attr.name", "description");
			this.streamWriter.writeAttribute("attr.type", "string");
			this.streamWriter.writeAttribute("for", "edge");
			this.streamWriter.writeAttribute("id", "d9");

			// Define the type 'edge'
			this.streamWriter.writeEmptyElement("key");
			this.streamWriter.writeAttribute("for", "edge");
			this.streamWriter.writeAttribute("id", "d10");
			this.streamWriter.writeAttribute("yfiles.type", "edgegraphics");
		} catch (XMLStreamException e) {
			throw new GraphMLException(e);
		}
	}

	public void endDocument() throws GraphMLException {
		assertNotClosed();

		try {
			// Close the <graphml> root tag
			this.streamWriter.writeEndElement();

			this.streamWriter.writeEndDocument();
		} catch (XMLStreamException e) {
			throw new GraphMLException(e);
		}
	}

	// --- Graph --- //

	public void startGraph() throws GraphMLException {
		assertNotClosed();

		try {
			// TODO Introduce parameters for the 2 attributes
			this.streamWriter.writeStartElement("graph");
			this.streamWriter.writeAttribute("edgedefault", "directed");
			this.streamWriter.writeAttribute("id", "G");

			// TODO Generate the <data key="d7"/>
		} catch (XMLStreamException e) {
			throw new GraphMLException(e);
		}
	}

	public void endGraph() throws GraphMLException {
		assertNotClosed();

		try {
			// Close the element <graph>
			this.streamWriter.writeEndElement();
		} catch (XMLStreamException e) {
			throw new GraphMLException(e);
		}
	}

	// --- Node --- //

	public String node(String label) throws GraphMLException {
		assertNotClosed();

		try {
			final String nodeId = nextNodeId();

			this.streamWriter.writeStartElement("node");
			this.streamWriter.writeAttribute("id", nodeId);

			// TODO Generate the <data key="d5"/> (node description)

			// Generate the tags for rendering the node
			this.streamWriter.writeStartElement("data");
			this.streamWriter.writeAttribute("key", "d6");

			this.streamWriter.writeStartElement("y:ShapeNode");

			// y:Geometry
			this.streamWriter.writeEmptyElement("y:Geometry");
			this.streamWriter.writeAttribute("height", "30.0");
			this.streamWriter.writeAttribute("width", "30.0");
			this.streamWriter.writeAttribute("x", "48.0");
			this.streamWriter.writeAttribute("y", "90.0");

			// y:Fill
			this.streamWriter.writeEmptyElement("y:Fill");
			this.streamWriter.writeAttribute("color", "#FFCC00");
			this.streamWriter.writeAttribute("transparent", "false");

			// y:BorderStyle
			this.streamWriter.writeEmptyElement("y:BorderStyle");
			this.streamWriter.writeAttribute("color", "#000000");
			this.streamWriter.writeAttribute("type", "line");
			this.streamWriter.writeAttribute("width", "1.0");

			// y:NodeLabel
			this.streamWriter.writeStartElement("y:NodeLabel");
			this.streamWriter.writeAttribute("alignement", "center");
			this.streamWriter.writeAttribute("fontFamily", "Dialog");
			this.streamWriter.writeAttribute("fontSize", "12");
			this.streamWriter.writeAttribute("fontStyle", "plain");
			this.streamWriter.writeAttribute("hasBackgroundColor", "false");
			this.streamWriter.writeAttribute("hasLineColor", "false");
			this.streamWriter.writeAttribute("textColor", "#000000");
			this.streamWriter.writeAttribute("visible", "true");
			this.streamWriter.writeCharacters(label);
			this.streamWriter.writeEndElement(); // </y:NodeLabel>

			// y:Shape
			this.streamWriter.writeEmptyElement("y:Shape");
			this.streamWriter.writeAttribute("type", "rectangle");

			this.streamWriter.writeEndElement(); // </y:ShapeNode>

			this.streamWriter.writeEndElement(); // </data>

			this.streamWriter.writeEndElement(); // </node>

			return nodeId;
		} catch (XMLStreamException e) {
			throw new GraphMLException(e);
		}
	}

	// --- Others --- //

	private String nextNodeId() {
		return String.format("n%d", nodeSequence.getAndIncrement());
	}

	private void assertNotClosed() {
		if (closed) {
			throw new IllegalStateException("The writer is closed");
		}
	}

	public void close() {
		assertNotClosed();

		if (streamWriter != null) {
			try {
				streamWriter.close();
			} catch (XMLStreamException e) {
				// Close quietly
			}
		}
		if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
				// Close quietly
			}
		}

		this.closed = true;
	}
}