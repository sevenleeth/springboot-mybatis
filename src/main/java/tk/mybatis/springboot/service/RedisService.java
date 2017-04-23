package tk.mybatis.springboot.service;

import java.util.List;

/**
 * Created by guangpo on 2017/4/23.
 */
public interface RedisService {
    /*public void set(String key, Object value);

    public Object get(String key);
*/
    public boolean set(String key, String value);

    public String query(String key);

    public boolean expire(String key,long expire);

    public <T> boolean setList(String key ,List<T> list);

    public <T> List<T> getList(String key,Class<T> clz);

    public long lpush(String key,Object obj);

    public long rpush(String key,Object obj);

    public String lpop(String key);
}
