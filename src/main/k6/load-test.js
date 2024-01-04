import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {

  stages: [
    { duration: '20s', target: 200 },
    { duration: '60s', target: 1000},
    { duration: '20s', target: 0 },
  ]
};

export default function () {
  const res = http.get('http://localhost:8080/api/search-vt')
  check(res, { 'status was 200': (r) => r.status == 200 })
  sleep(1)
}