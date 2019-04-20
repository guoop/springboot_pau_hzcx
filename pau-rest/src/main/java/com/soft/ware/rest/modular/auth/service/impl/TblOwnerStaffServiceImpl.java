package com.soft.ware.rest.modular.auth.service.impl;

/*

@Service
@Transactional
public class TblOwnerStaffServiceImpl extends BaseService<TblOwnerStaffMapper,TblOwnerStaff> implements TblOwnerStaffService {

    */
/*@Resource
    private TblOwnerStaffMapper mapper;*//*


    @Autowired
    private TblOwnerService tblOwnerService;

    @Autowired
    private ImService imService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    */
/*@Override
    public TblOwnerStaff findByPhone(String phone) {
        return mapper.selectOne(new TblOwnerStaff().setPhone(phone));
    }

    @Override
    public TblOwnerStaff find(SessionUser user) {
        return mapper.selectOne(new TblOwnerStaff().setId(Integer.valueOf(user.getId())));
    }
*//*

    @Override
    public boolean saveOrUpdate(SessionUser user, StaffEditParam param) throws Exception {
        TblOwner o = tblOwnerService.find(user);
        if (o != null && param.getPhone().equals(o.getPhone())) {
            throw new PauException(BizExceptionEnum.PHONE_EXISTS);
        }
        TblOwnerStaff p = this.findByPhone(param.getPhone());
        TblOwnerStaff s = null;
        if (ToolUtil.isEmpty(param.getId())) {
            if (p != null) {
                throw new PauException(BizExceptionEnum.PHONE_EXISTS);
            }
        }else {
            s = this.selectById(param.getId());

            if (p != null && !p.getId().equals(s.getId())) {
                throw new PauException(BizExceptionEnum.PHONE_EXISTS);
            }
        }
        if (s == null) {
            //添加店员信息
            s = new TblOwnerStaff();
           // param.update(s);
            s.setOwner(user.getOwnerId());
            s.setCreatedAt(new Date());
            Integer row = mapper.insert(s);
            //添加到im群组
            if (s.getPassword() != null && s.getPassword().length() > 10) {
                //imService.syncUsers(user,o, s);
            }
            if (row != 1) {
                throw new PauException(BizExceptionEnum.ADD_ERROR);
            }
        } else {
            //修改店员信息
            //param.update(s);
            //全部更新
            if (!s.getOwner().equals(user.getOwnerId())) {
                throw new PauException(BizExceptionEnum.ERROR);
            }
            Integer row = mapper.updateAllColumnById(s);
            String keyPrefix = "user:" + s.getPhone() + ":*";
            Set<String> keys = redisTemplate.keys(keyPrefix);
            //imService.syncUsers(user, o, s);
            if (TblOwnerStaff.status_0.equals(param.getStatus())) {
                //启用店员信息
                //todo yancc 可能还需要更新im群组信息
                for (String key : keys) {
                    redisTemplate.opsForHash().putAll(key, BeanMapUtils.toMap(s,true));
                }
            } else {
                // 禁用店员信息
                //todo yancc 可能还需要删除im群组信息
                redisTemplate.delete(keys);
            }
            if (row != 1) {
                throw new PauException(BizExceptionEnum.UPDATE_ERROR);
            }
        }

        return true;
    }

    @Override
    public List<TblOwnerStaff> selectAll(SessionUser user) {
        return mapper.selectList(new EntityWrapper<TblOwnerStaff>().eq("owner", user.getOwnerId()).ne("status", TblOwnerStaff.status_2));
    }

}*/
