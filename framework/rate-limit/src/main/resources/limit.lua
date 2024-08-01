local count = redis.call('get',KEYS[1])
local EXPIRE = tonumber(ARGV[2])
local LIMIT = tonumber(ARGV[1])

if count then
    if tonumber(count) > LIMIT then
        return tostring(count)
    end
    count = redis.call('INCR',KEYS[1])
else
    redis.call('set',KEYS[1],1)
    redis.call("EXPIRE", KEYS[1], EXPIRE)
    count = "1"
end


return tostring(count)