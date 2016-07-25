package com.broxhouse.h5api

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import javax.ws.rs.core.Response

/**
 * JSonHelper contains methods useful for handling JSON responses.
 * Created by Randall Stauffer on 4/25/2016.
 */
class JSonHelper {


    /**
     * Get a specific item from a JSONArray given the response.
     *
     * @param response
     *            - REST Response object
     * @param arrayName
     *            - Name of the array containing the item you want
     * @param keyToFind
     *            - Name of the Key Field you want to match on
     * @param itemToFind
     *            - Name of the specific key you are looking for
     * @param fieldToReturn
     *            - name of the field whose value you want returned (generally different than the one you are matching on)
     * @return String which contains the requested value.
     */
    public static String getJSONArrayItem (Response response, String arrayName, String keyToFind, String itemToFind, String fieldToReturn) {
        JSONObject jObj=JSonHelper.getJSONObject(response);
        return JSonHelper.getJSONArrayItem(jObj,arrayName,keyToFind,itemToFind,fieldToReturn);
    }

    /**
     * Get a specific item from a JSONArray given the JSONObject response.
     *
     * @param jObj
     *            - JSONObject returned from the REST request
     * @param arrayName
     *            - Name of the array containing the item you want
     * @param keyToFind
     *            - Name of the Key Field you want to match on
     * @param itemToFind
     *            - Name of the specific key you are looking for
     * @param fieldToReturn
     *            - name of the field whose value you want returned (generally different than the one you are matching on)
     * @return String which contains the requested value.
     */
    public static String getJSONArrayItem (JSONObject jObj, String arrayName, String keyToFind, String itemToFind, String fieldToReturn) {
        JSONArray jArray=null
        String gotcha=null
        try
        {
            jArray = getJSONArray(jObj,arrayName)
        }
        catch (JSONException)
        {

        }
        jArray.each
        {
            if (it."$keyToFind".equals((String) itemToFind)) {
            gotcha=it."$fieldToReturn"
        }
        }
        return gotcha;
    }

    /**
     * Get a JSONObject from a REST response.
     *
     * @param response
     *            - the REST response
     * @return JSONObject object created from the response.
     */
    public static JSONObject getJSONObject (Response response) {
        JSONObject obj = null
        def body=response.readEntity(String.class)

        if(true || !(200..<300).contains(response.getStatus()))
        {

        }
        try
        {
            obj=new JSONObject(body)
        }
        catch (JSONException e)
        {
            throw e
        }
        return new JSONObject(body);
    }

    /**
     * Get a sub object from a JSONObject.
     *
     * @param object
     *            - the JSON object
     * @param item
     *            - the sub item to get
     * @return JSONObject object created from parent object.
     */
    public static JSONObject getJSONObject (JSONObject object, String item) {
        JSONObject obj = null
        Boolean hasItem = false;

        hasItem=objectHasProperty(object, item)
        obj=object.getJSONObject(item)
        return obj
    }

    /**
     * Get a JSONArray from a JSONObject.
     *
     * @param object
     *            - the JSON object
     * @param item
     *            - the sub item to get
     * @return JSONArray object created from parent object.
     */
    public static JSONArray getJSONArray (JSONObject object, String item) {
        JSONArray array = null
        Boolean hasItem = false;

        hasItem=objectHasProperty(object,item)
        array=object.getJSONArray(item)
        return array
    }

    /**
     * Determines if a JSONObject has a given property.
     *
     * @param object
     *            - the JSON object
     * @param property
     *            - the sub item to get
     * @return Boolean true if it does, false if it doesn't (also logs an error containing the content of the object if it is not there).
     */
    public static Boolean objectHasProperty (JSONObject object, String property) {
        JSONObject obj = null
        Boolean hasItem = false;

        hasItem=object.has(property)
        if(!hasItem)
        {

        }
        return hasItem
    }

}
