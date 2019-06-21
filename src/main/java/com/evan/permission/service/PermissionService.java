package com.evan.permission.service;


import org.springframework.security.access.ConfigAttribute;

import java.util.Collection;
import java.util.Map;

public interface PermissionService {
    /**
     * 说明：这里map中的value是Collection<ConfigAttribute>的原因是在SecurityMetadataSource接口的getAttributes方法
     * 的返回值就是Collection<ConfigAttribute>类型，这样方便在匹对正确的情况下直接返回。
     * @return
     */
    Map<String, Collection<ConfigAttribute>> getPermissionMap();
}
