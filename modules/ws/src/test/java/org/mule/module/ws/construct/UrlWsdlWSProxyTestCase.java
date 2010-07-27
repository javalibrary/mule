/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.ws.construct;

import java.net.URI;

import org.mule.api.endpoint.OutboundEndpoint;

public class UrlWsdlWSProxyTestCase extends AbstractWSProxyTestCase
{
    @Override
    protected WSProxy newWSProxy(OutboundEndpoint testOutboundEndpoint) throws Exception
    {
        return new WSProxy(muleContext, "url-wsdl-ws-proxy", directInboundMessageSource,
            testOutboundEndpoint, new URI("test://test?wsdl"));
    }
}
