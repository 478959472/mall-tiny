
import request from '@/utils/request'
export function listMyVideo() {
  return request({
    url: '/laihua/my-videos',
    method: 'get'
  })
}
