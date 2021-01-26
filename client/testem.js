'use strict';

const fs = require('fs');
const path = require('path');

const config = {
  test_page: 'tests/index.html?hidepassed',
  disable_watching: true,
  launch_in_ci: ['Chrome'],
  launch_in_dev: ['Chrome'],
  browser_start_timeout: 120,
  browser_args: {
    Chrome: {
      ci: [
        // --no-sandbox is needed when running Chrome inside a container
        process.env.CI ? '--no-sandbox' : null,
        '--headless',
        '--disable-dev-shm-usage',
        '--disable-software-rasterizer',
        '--mute-audio',
        '--remote-debugging-port=0',
        '--window-size=1440,900',
      ].filter(Boolean),
    },
  },
};

if (process.env.CHROME_BIN && fs.existsSync(process.env.CHROME_BIN)) {
  config['browser_paths'] = {
    Chrome: path.dirname(process.env.CHROME_BIN),
  };
  config['browser_exes'] = {
    Chrome: path.basename(process.env.CHROME_BIN),
  };
}

module.exports = config;
