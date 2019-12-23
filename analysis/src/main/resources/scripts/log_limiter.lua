local happen_key = KEYS[1]
local merge_key = KEYS[2]

local cal_num = tonumber(ARGV[1])
local cal_time = tonumber(ARGV[2]) * 1000
local send_interval = tonumber(ARGV[3]) * 1000

local is_merge = redis.call('EXISTS', merge_key)
if is_merge > 0 then
    return 1
end

local t = redis.call('TIME')
local now_time = t[1] * 1000 + math.floor(t[2] / 1000)
redis.replicate_commands();
local cur_len = redis.call('RPUSH', happen_key, now_time)

if cur_len <= cal_num then
    return 0
end

local val = redis.call('LINDEX', happen_key, -(cal_num + 1))
if now_time - val > cal_time then
    redis.call('LTRIM', happen_key, -cal_num, -1)
    return 0
end

redis.call('del', happen_key)
local merge_val = t[1] * 1000000 + t[2]
redis.call('SET', merge_key, merge_val, 'NX', 'PX', send_interval)
return 1