'use strict';

describe('Service: hscsession', function () {

  // load the service's module
  beforeEach(module('appApp'));

  // instantiate service
  var hscsession;
  beforeEach(inject(function (_hscsession_) {
    hscsession = _hscsession_;
  }));

  it('should do something', function () {
    expect(!!hscsession).toBe(true);
  });

});
