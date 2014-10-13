package org.familysearch.gal.shared.common;

import static javax.ws.rs.core.MediaType.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Variant;

import org.springframework.util.StringUtils;

/**
 * MediaTypes for GAL API
 */
public class GALV1MediaTypes {

    private static final String APPLICATION = "application";
    private static final String XML = "vnd.fs-gal-v1+xml";
    private static final String JSON = "vnd.fs-gal-v1+json";

    public static final MediaType APPLICATION_GAL_V1_XML_TYPE = new MediaType(APPLICATION, XML);
    public static final MediaType APPLICATION_GAL_V1_JSON_TYPE = new MediaType(APPLICATION, JSON);

    public static final String APPLICATION_GAL_V1_XML = "application/vnd.fs-gal-v1+xml";
    public static final String APPLICATION_GAL_V1_JSON = "application/vnd.fs-gal-v1+json";

    public static final List<Variant> VARIANTS;

    static {
        VARIANTS = Variant.mediaTypes(APPLICATION_XML_TYPE,
                                      APPLICATION_JSON_TYPE,
                                      APPLICATION_ATOM_XML_TYPE,
                                      APPLICATION_FORM_URLENCODED_TYPE,
                                      APPLICATION_GAL_V1_XML_TYPE,
                                      APPLICATION_GAL_V1_JSON_TYPE,
                                      WILDCARD_TYPE)
            .add()
            .build();
    }

    public static Variant newXMLVariant() {
        return new Variant(APPLICATION_GAL_V1_XML_TYPE, null, null);
    }

    public static Variant newJSONVariant() {
        return new Variant(APPLICATION_GAL_V1_JSON_TYPE, null, null);
    }

    public static Variant primaryVariant(Request request, String type) {
        Variant variant = request.selectVariant(VARIANTS);

        String subtype = XML;
        if (variant.getMediaType().getSubtype().contains("json")) {
            subtype = JSON;
        }

        MediaType mediaType = new MediaType(APPLICATION, subtype, parameterMap(type));

        return new Variant(mediaType, null, null);
    }

    /**
     * Returns Primary Variant for an atom feed.
     */
    public static Variant primaryAtomFeedVariant(Request request) {
        Variant variant = request.selectVariant(VARIANTS);

        MediaType mediaType = APPLICATION_ATOM_XML_TYPE;
        if (variant.getMediaType().getSubtype().contains("json")) {
            mediaType = APPLICATION_JSON_TYPE;
        }

        return new Variant(mediaType, null, null);
    }

    public static Variant alternateVariant(Request request, String type) {
        Variant variant = request.selectVariant(VARIANTS);

        String subtype = XML;
        if (variant.getMediaType().getSubtype().contains("xml")) {
            subtype = JSON;
        }

        MediaType mediaType = new MediaType(APPLICATION, subtype, parameterMap(type));

        return new Variant(mediaType, null, null);
    }

    public static MediaType galXMLMediaType(String type) {
        return new MediaType(APPLICATION, XML, parameterMap(type));
    }

    public static MediaType galJSONMediaType(String type) {
        return new MediaType(APPLICATION, JSON, parameterMap(type));
    }

    private static Map<String, String> parameterMap(String type) {
        Map<String, String> parameters = null;

        if (!StringUtils.isEmpty(type)) {
            parameters = new HashMap<String, String>();
            parameters.put("type", type);
        }

        return parameters;
    }
}
