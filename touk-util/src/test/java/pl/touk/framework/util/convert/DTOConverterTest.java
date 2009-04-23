/*
 * Copyright (c) (2005 - )2009 TouK sp. z o.o. s.k.a.
 * All rights reserved
 */

package pl.touk.framework.util.convert;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;

import java.util.List;
import java.util.ArrayList;
/*
 * TODO: ADD SHORT DESCRIPTION 
 *
 * @author <a href="mailto:jkr@touk.pl">Jakub Kurlenda</a>
 */

public class DTOConverterTest {

    public List<DummyClass> createDummyCollection() {
        List<DummyClass> dummyDTOs = new ArrayList<DummyClass>();
        for (int i = 0; i < 5; i++) {
            DummyClass aClass = new DummyClass("asdaw" + i, (i % 2 == 0), i, 3);
            dummyDTOs.add(aClass);
        }
        return dummyDTOs;
    }

    @Test
    public void testConvert() {
        DummyDTO dto = new DummyDTO("xxx", true, 77, 18);
        dto.setList(createDummyCollection());

        DummyClass dummyClass = DTOConverter.convert(dto, DummyClass.class);
        Assert.assertNotNull(dummyClass);

        Assert.assertEquals(dummyClass.getAaa(), dto.getAaa(), 0);

        Assert.assertTrue(dummyClass.getList().size() > 0);

        Assert.assertEquals(dummyClass.getList().size(), dto.getList().size());

        Assert.assertTrue(dummyClass.getList().get(0) instanceof DummyDTO);

        Assert.assertEquals(dummyClass.getList().get(0).getAaa(), dto.getList().get(0).getAaa(), 0);
    }

    @Test
    public void testCollectionCopy() {
        List<DummyClass> dummyClasses = createDummyCollection();

        List<DummyDTO> dummyDTOs = DTOConverter.convert(dummyClasses, DummyDTO.class);

        Assert.assertEquals(dummyClasses.size(), dummyDTOs.size());
    }
    
}
