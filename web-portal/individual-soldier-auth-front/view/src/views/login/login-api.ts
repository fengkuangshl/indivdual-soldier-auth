import request from '@/fetch'
import { LoginRequest } from './interface'
import { LoginResponse } from './interface/response'

export const LoginApi = (data: LoginRequest): Promise<KWResponse.Result<LoginResponse>> => request.post('user/login', data)
