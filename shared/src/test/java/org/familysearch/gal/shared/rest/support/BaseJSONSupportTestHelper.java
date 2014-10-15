package org.familysearch.gal.shared.rest.support;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BaseJSONSupportTestHelper {

    public BaseJSONSupportTestHelper() {
        super();
    }

    protected <T> T assertJSONMarshalling(Class<T> typeToken, T entity, BaseJSONSupport<T> jsonSupport) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        jsonSupport.writeTo(entity, outStream);

        ByteArrayInputStream inStream = new ByteArrayInputStream(outStream.toByteArray());

        T actual = jsonSupport.readFrom(inStream);

        assertThat(actual, is(notNullValue()));
        assertThat(actual, is(not(entity)));

        return actual;
    }

}
