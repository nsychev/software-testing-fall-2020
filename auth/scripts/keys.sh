#!/bin/sh

mkdir keys 2>/dev/null
openssl ecparam -genkey -name secp521r1 -noout -out keys/private.pem
openssl ec -in keys/private.pem -pubout -out keys/public.pem
