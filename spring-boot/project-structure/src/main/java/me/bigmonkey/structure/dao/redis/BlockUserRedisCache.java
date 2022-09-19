package me.bigmonkey.structure.dao.redis;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.etoos.member.common.cache.RedisCacheNames;
import com.etoos.member.common.enums.ServiceCode;
import com.etoos.member.dto.cache.BlockUser;

@Component
public class BlockUserRedisCache {

    private HashOperations<String, String, BlockUser> hashOperations;

    @Resource(name = "ServiceObjectTemplate")
    private RedisTemplate<String, BlockUser> redisTemplate;

    final String KEY_SPACE = RedisCacheNames.MEMBER_BLOCK;
    final String KEY_SEPARATOR = RedisCacheNames.REDIS_CACHE_KEY_SEPARATOR;

    @PostConstruct
    public void init() {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public List<BlockUser> findMemberBlocksInfo(Long memberNo, ServiceCode serviceCode) {
        return hashOperations
            .values(KEY_SPACE + KEY_SEPARATOR + memberNo + KEY_SEPARATOR + serviceCode.toString());
    }

    public BlockUser findMemberBlockInfoByField(Long memberNo, ServiceCode serviceCode, String blockSeq) {
        return hashOperations
            .get(KEY_SPACE + KEY_SEPARATOR + memberNo + KEY_SEPARATOR + serviceCode.toString(), blockSeq);
    }

    public void save(Long memberNo, ServiceCode serviceCode, List<BlockUser> memberBlocks) {
        memberBlocks.stream().forEach(memberBlock -> {
            hashOperations.put(KEY_SPACE + KEY_SEPARATOR + memberNo + KEY_SEPARATOR + serviceCode.toString(),
                memberBlock.getSeqMemberBlock().toString(), memberBlock);
        });
    }

    public void deleteByField(Long memberNo, ServiceCode serviceCode, Long field) {
        hashOperations.delete(KEY_SPACE + KEY_SEPARATOR + memberNo + KEY_SEPARATOR + serviceCode.toString(),
            Long.toString(field));
    }

    public void deleteAll(Long memberNo, ServiceCode serviceCode) {
        hashOperations.getOperations()
            .delete(KEY_SPACE + KEY_SEPARATOR + memberNo + KEY_SEPARATOR + serviceCode.toString());
    }
}

