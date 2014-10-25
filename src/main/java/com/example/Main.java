package com.example;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.eclipse.persistence.jaxb.JAXBContext;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.JAXBMarshaller;
import org.opentravel.ota._2003._05.InvCountType;
import org.opentravel.ota._2003._05.OTAHotelInvCountNotifRQ;
import org.opentravel.ota._2003._05.ObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static final String ALT_LANG_ID = "alt lang id fnord";
    private static final String AREA_ID = "areaID_fnord";
    private static final String UNIQUE_ID_ID = "inventory unique id fnord";

    public static void main(String[] args) throws JAXBException, XMLStreamException {
        OTAHotelInvCountNotifRQ orig = gen();

        JAXBContext ctx = (JAXBContext) JAXBContextFactory.createContext(OTAHotelInvCountNotifRQ.class.getPackage().getName(), Main.class.getClassLoader());

        StringWriter writer = new StringWriter();
        JAXBMarshaller ma = ctx.createMarshaller();
        ma.setProperty(JAXBMarshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        ma.marshal(orig, writer);
        String xml = writer.toString();
        logger.debug("\n" + xml);

        StringReader reader = new StringReader(xml);
        OTAHotelInvCountNotifRQ copy = (OTAHotelInvCountNotifRQ) ctx.createUnmarshaller().unmarshal(reader);
        assert copy.getInventories().getInventory().get(0).getUniqueID().getID() != null;
        assertThat("sanity check (unique id)", copy.getInventories().getInventory().get(0).getUniqueID().getID(), is(UNIQUE_ID_ID));

        String altLangId = ctx.getValueByXPath(copy, "@AltLangID", null, String.class);
        assertThat("rq's altlang attr", altLangId, is(ALT_LANG_ID));

        InvCountType inventories = ctx.getValueByXPath(copy, "Inventories", null, InvCountType.class);
        assertThat("inventories", inventories, is(not(nullValue())));
        assertThat("inventories's area id attr", inventories.getAreaID(), is(AREA_ID));
        assertThat("inventories's unique id id attr", inventories.getInventory().get(0).getUniqueID().getID(), is(UNIQUE_ID_ID));
    }

    private static OTAHotelInvCountNotifRQ gen() {
        ObjectFactory fac = new ObjectFactory();
        OTAHotelInvCountNotifRQ rq = fac.createOTAHotelInvCountNotifRQ();
        rq.setAltLangID(ALT_LANG_ID);
        rq.setInventories(fac.createInvCountType());
        rq.getInventories().setAreaID(AREA_ID);
        rq.getInventories().getInventory().add(fac.createBaseInvCountType());
        rq.getInventories().getInventory().get(0).setUniqueID(fac.createUniqueIDType());
        rq.getInventories().getInventory().get(0).getUniqueID().setID(UNIQUE_ID_ID);
        return rq;
    }

}
