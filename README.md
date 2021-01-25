###174 nginx配置
cd /opt/nginx  
sudo ./sbin/nginx -s reload

```
 ./configure --prefix=/opt/nginx --with-http_ssl_module --add-module=/home/montnets/chenyun/src/headers-more-nginx-module-0.33
	 
 make
 make install
 
  server {
    listen       8033;
    server_name  localhost;
 
    location / {
    proxy_pass  https://common.laihua.com;

    #Proxy Settings
    proxy_redirect     off;
    proxy_set_header   Cookie  EGG_SESS=Q1b81H8GGq2-ys4GlXHnUZHr2DMTROnUhp72Ccs7QVydX-RAdAdEqpoH3e4pc1GS;  
	#       $host;不能使用$host变量
    proxy_set_header   X-Real-IP        $remote_addr;
    proxy_set_header   X-Forwarded-For  $proxy_add_x_forwarded_for;
    proxy_next_upstream error timeout invalid_header http_500 http_502 http_503 http_504;
    proxy_max_temp_file_size 0;
    proxy_connect_timeout      90;
    proxy_send_timeout         90;
    proxy_read_timeout         90;
    proxy_buffer_size          4k;
    proxy_buffers              4 32k;
    proxy_busy_buffers_size    64k;
    proxy_temp_file_write_size 64k;
    }
```

