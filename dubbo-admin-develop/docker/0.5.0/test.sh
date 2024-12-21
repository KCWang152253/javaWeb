# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at

#     http://www.apache.org/licenses/LICENSE-2.0

# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

LOOP_SIZE=60
i=0

while [[ $i -lt LOOP_SIZE ]]; do
	status_code=$(curl --write-out %{http_code} --silent --output /dev/null http://admin:8080)

  if [[ "$status_code" -eq 200 ]] ; then
    echo "Tests passed!"
    exit 0
  else
    curl -v http://admin:8080
    echo "status is incorrect, waiting for next turn"
  fi
	sleep 5
	i=$i+1
done

echo "Tests failed!"
exit 1