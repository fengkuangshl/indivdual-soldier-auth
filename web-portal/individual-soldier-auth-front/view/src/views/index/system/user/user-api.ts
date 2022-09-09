import request from '@/fetch'
import { LoginSuccessUserInfo, UserInfo, UserSearchRequest, UserStatusChange, UserForm, ModifyPassword } from './interface/sys-user'

export const UserInfoApi = (): Promise<KWResponse.Result<LoginSuccessUserInfo>> => request.get('user/getLoginApp')
export const UserPagedApi = (pageRequest: KWRequest.PageRequest<UserSearchRequest>): Promise<KWResponse.PageResult<UserInfo>> => request.post('user/getSysUserByPaged', pageRequest)
export const UserStatusChangeRequestApi = (param: UserStatusChange): Promise<KWResponse.Result> => request.post('user/updateEnabled', param)
export const UserGetApi = (id: number): Promise<KWResponse.Result<UserInfo>> => request.get('user/getUserFullById/' + id)
export const UserSaveOrUpdateApi = (userInfo: UserForm): Promise<KWResponse.Result> => request.post('user/saveOrUpdate', userInfo)
export const ResetPasswordApi = (id: number): Promise<KWResponse.Result<UserInfo>> => request.get('user/resetPassword/' + id)
export const UpdateMeApi = (userInfo: UserForm): Promise<KWResponse.Result<UserInfo>> => request.put('user/me', userInfo)
export const UpdatePasswordApi = (modifyPassword: ModifyPassword): Promise<KWResponse.Result<UserInfo>> => request.post('user/modifyMyPassword', modifyPassword)
export const LogoutApi = (): Promise<KWResponse.Result> => request.get('user/logout')
export const GetAllUserAndState = (): Promise<KWResponse.Result<Array<UserInfo>>> => request.get('user/getAllUserAndState')
