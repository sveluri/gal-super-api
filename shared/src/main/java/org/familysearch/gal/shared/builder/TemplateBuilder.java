package org.familysearch.gal.shared.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.FormParam;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.familysearch.gal.shared.model.Template;
import org.springframework.util.StringUtils;

/**
 * Builder for a {@link org.familysearch.gal.shared.model.Template} instance. This only supports a sub-set of the
 * expandable members as defined in RFC 6570. (See: <a>http://tools.ietf.org/html/rfc6570</a>).
 *
 */
public class TemplateBuilder {

    private final static Map<String, String> pathCache = new ConcurrentHashMap<String, String>();
    private final static Map<String, List<String>> queryParamCache = new ConcurrentHashMap<String, List<String>>();

    private final String baseURI;
    private List<String> paths = new ArrayList<String>();
    private List<String> queryKeys = new ArrayList<String>();
    private Map<String, String> queryKeyValue = new HashMap<String, String>();
    private String rel;
    private String type;

    public TemplateBuilder(String baseURI) {
        this.baseURI = baseURI;
    }

    /**
     * Add the specified path.
     * 
     * @param path
     * @return builder
     */
    public TemplateBuilder path(String path) {
        path = path.startsWith("/") ? path : "/" + path;
        path = path.endsWith("/") ? path.substring(0, path.lastIndexOf("/")) : path;

        paths.add(path);
        return this;
    }

    /**
     * Add the value of the @Path annotation for the specified endpoint class.
     * 
     * @param endpointClass
     * @return the builder
     * @throws IllegalArgumentException if the class does not have a @Path annotation
     */
    public TemplateBuilder path(Class<?> endpointClass) {
        String key = endpointClass.getCanonicalName();
        if (!pathCache.containsKey(key)) {
            Path path = endpointClass.getAnnotation(Path.class);

            if (path == null)
                throw new IllegalArgumentException("No @Path annotation found on class: "
                                                   + endpointClass.getCanonicalName());

            pathCache.put(key, path.value());
        }

        paths.add(pathCache.get(key));

        return this;
    }

    /**
     * Add the value of the @Path annotation for the specified method of the endpoint class.
     * 
     * @param endpointClass
     * @param methodName
     * @return the builder
     * @throws IllegalArgumentException if the method does not exist on the class
     * @throws IllegalArgumentException if the method does not have a @Path annotation
     */
    public TemplateBuilder path(Class<?> endpointClass, String methodName) {
        String key = endpointClass.getCanonicalName() + "." + methodName;
        if (!pathCache.containsKey(key)) {
            Method method = findMethod(endpointClass, methodName);
            Path path = method.getAnnotation(Path.class);

            if (path == null)
                throw new IllegalArgumentException("No @Path annotation found on method: "
                                                   + endpointClass.getCanonicalName() + "." + methodName);

            pathCache.put(key, path.value());
        }

        paths.add(pathCache.get(key));

        return this;
    }

    /**
     * Add the list of variables as path variables.
     * 
     * @param vars
     * @return the builder
     */
    public TemplateBuilder pathVars(String... vars) {
        for (String var : vars) {
            paths.add("/{" + var + "}");
        }

        return this;
    }

    /**
     * Add the value of any @QueryParam annotations found on the parameter list for the
     * specified method of the endpoint class. (NOTE: when built, all query parameters
     * will be sorted by name.)
     * 
     * @param endpointClass
     * @param methodName
     * @return the builder
     * @throws IllegalArgumentException if the method does not exist on the class
     */
    public TemplateBuilder queryParams(Class<?> endpointClass, String methodName) {
        String key = endpointClass.getCanonicalName() + "." + methodName;
        if (!queryParamCache.containsKey(key)) {
            List<String> keys = new ArrayList<String>();
            Method method = findMethod(endpointClass, methodName);

            for (Annotation[] paramAnnotations : method.getParameterAnnotations()) {
                for (Annotation annotation : paramAnnotations) {
                    if (annotation.annotationType().equals(QueryParam.class)) {
                        QueryParam queryParam = (QueryParam) annotation;
                        String queryKey = queryParam.value();
                        keys.add(queryKey);
                    }
                    else if (annotation.annotationType().equals(FormParam.class)) {
                        FormParam formParam = (FormParam) annotation;
                        String formKey = formParam.value();
                        keys.add(formKey);
                    }
                }
            }

            queryParamCache.put(key, keys);
        }

        queryKeys.addAll(queryParamCache.get(key));

        return this;
    }

    /**
     * Add the list of query parameters.
     * 
     * @param queryParams
     * @return the builder
     */
    public TemplateBuilder queryParams(String... queryParams) {
        for (String queryKey : queryParams) {
            queryKeys.add(queryKey);
        }

        return this;
    }

    /**
     * Add the value of query parameter.
     * 
     * @param queryKey
     * @param queryValue
     * @return the builder
     */
    public TemplateBuilder queryParamValue(String queryKey, String queryValue) {
        queryKeyValue.put(queryKey, queryValue);
        return this;
    }

    /**
     * Set a relation.
     * 
     * @param rel
     * @return the builder
     */
    public TemplateBuilder rel(String rel) {
        this.rel = rel;

        return this;
    }

    /**
     * Set a media type.
     * 
     * @param type
     * @return the builder
     */
    public TemplateBuilder type(String type) {
        this.type = type;

        return this;
    }

    /**
     * Set a media type.
     * 
     * @param type
     * @return the builder
     */
    public TemplateBuilder type(MediaType type) {
        this.type = type.toString();

        return this;
    }

    /**
     * Build the Template instance. This sorts the list of query parameters in
     * alphabetical order.
     * 
     * @return Template
     */
    public Template build() {
        StringBuilder uri = new StringBuilder(baseURI);
        for (String path : paths) {
            uri.append(path);
        }

        if (!queryKeys.isEmpty()) {
            Collections.sort(queryKeys);

            String prefix = uri.toString().contains("?") ? "&" : "?";
            List<String> queryKeyWithNoValue = new ArrayList<String>();
            for (String queryKey : queryKeys) {
                if (queryKeyValue.containsKey(queryKey)) {
                    uri.append(prefix).append(queryKey + "=" + queryKeyValue.get(queryKey));
                    prefix = uri.toString().contains("?") ? "&" : "?";
                }
                else {
                    queryKeyWithNoValue.add(queryKey);
                }
            }
            if (queryKeyWithNoValue.size() > 0) {
                uri.append("{");
                uri.append(prefix);
                uri.append(StringUtils.collectionToCommaDelimitedString(queryKeyWithNoValue));
                uri.append("}");
            }
        }

        return new Template.Builder()
            .pattern(uri.toString())
            .rel(rel)
            .type(type)
            .build();
    }

    private Method findMethod(Class<?> endpointClass, String methodName) {
        Method[] methods = endpointClass.getMethods();
        Method theMethod = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                theMethod = method;
                break;
            }
        }

        if (theMethod == null) {
            throw new IllegalArgumentException("Method not found on class: " + endpointClass.getCanonicalName()
                                               + "; method: " + methodName);
        }
        return theMethod;
    }

}
