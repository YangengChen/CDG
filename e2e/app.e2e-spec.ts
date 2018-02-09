import { CDGPage } from './app.po';

describe('cdg App', () => {
  let page: CDGPage;

  beforeEach(() => {
    page = new CDGPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});
